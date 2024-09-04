package cn.njcit.controller;

import cn.njcit.entity.User;
import cn.njcit.service.IClassService;
import cn.njcit.service.IUserService;
import cn.njcit.util.ResponseResult;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author njcit
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
    public ResponseResult studentManage(@RequestParam(defaultValue = "1")Integer page,
                                        @RequestParam(required = false) String searchName){
        PageInfo<User> studentList=userService.getStudentList(page,searchName);
        return ResponseResult.ok().put("studentList",studentList);
    }
}
