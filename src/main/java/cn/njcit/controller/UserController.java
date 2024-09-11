package cn.njcit.controller;

import cn.njcit.entity.OIClass;
import cn.njcit.entity.User;
import cn.njcit.service.IClassService;
import cn.njcit.service.IUserService;
import cn.njcit.util.ResponseResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mashiro
 * @since 2024-09-03
 */

@RestController
@RequestMapping("/studentManage")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IClassService classService;

    @GetMapping("/list")
    public ResponseResult studentManage(@RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(required = false) String searchName){
        PageInfo<User> studentList = userService.getStudentList(page, searchName);
        return ResponseResult.ok().put("studentList",studentList);
    }

    @GetMapping("/edit")
    public ResponseResult studentEdit (@RequestParam(required = false) Long userId){
        ResponseResult responseResult = ResponseResult.ok();
        if(userId != null){
            User user = userService.getStudentById(userId);
            responseResult.put("student",user);
        }
        List<OIClass> classes = classService.list();
        responseResult.put("classes",classes);
        return responseResult;
    }
    @PostMapping("save")
    public ResponseResult saveStudent(User user, @RequestParam(required = false) MultipartFile filePath,
                                      @RequestParam(required = false) boolean resetPassword) throws IOException, InterruptedException {
        if (user.getUserId() == null) {  // 用户对象的用户id是null，表示新增
            // 先判断用户名(学号)是否被占用, 合成条件: 未冻结, 且role_id是4(学生), 且用户名是新增用户名
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("is_lock", 0).eq("role_id", 4).eq("user_name", user.getUserName());
            if (userService.count(wrapper) > 0) {  // 统计符合条件的记录数, 如果大于0, 表示用户名被占用了
                return ResponseResult.error("用户名被占用, 请换一个!");  // 用户名被占用, 返回错误信息
            }
            // 执行到此处表示用户名未被占用, 设置新学生的某些固定信息(不在新增学生的表单中)
            user.setRoleId(4L);  // 设置role_id是4
            user.setIsLock(0);   // 设置未冻结
        }
        String imgpath = userService.upload(filePath); // 上传头像(新增和修改均有可能)
        if (!StringUtil.isEmpty(imgpath)) {  // 如果头像上传成功, 上传后的文件名不为空
            user.setImgPath(imgpath);  // 将上传后的文件名设置到学生对象的img_path字段
        } else if (user.getUserId() == null) {  // 如果上传后的文件名为null, 且是新增用户(没有选择头像)
            user.setImgPath("oasys.jpg");  // 为学生设置一个固定的头像
        }
        // 注意, 如果是修改信息, 且没有重新上传头像, 维持user对象里的imgPath不变, 即不更改本来的头像
        if (resetPassword) {
            user.setPassword("123456"); // 如果用户选择重置密码, 重置密码(新增学生时此句总是执行, 所有学生密码默认设置为123456)
        }
        user.setModifyTime(LocalDateTime.now()); // 设置修改时间
        return userService.saveOrUpdate(user) ? ResponseResult.ok("保存成功!") : ResponseResult.error("保存失败!"); // 执行更新或插入
    }

    @DeleteMapping("/delete")
    public ResponseResult deleteStudent(Long userId) {
        User user = new User();
        user.setUserId(userId);
        user.setIsLock(1);
        return userService.updateById(user)?ResponseResult.ok("成功删除!") : ResponseResult.error("删除失败!");
    }

}
