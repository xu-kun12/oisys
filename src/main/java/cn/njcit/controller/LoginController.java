package cn.njcit.controller;

import cn.njcit.entity.User;
import cn.njcit.entity.UserLoginRecord;
import cn.njcit.service.IClassService;
import cn.njcit.service.IUserLoginRecordService;
import cn.njcit.service.IUserService;
import cn.njcit.util.ResponseResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/")
public class LoginController {
    @Autowired
    private IUserService userService;//注入IUserService
    @Autowired
    private IUserLoginRecordService userLoginRecordService;
    @Autowired
    private IClassService classService;

    //登录，根据用户名和密码查找
    @PostMapping(value = "login")
    public ResponseResult loginCheck(String userName, String password, HttpSession session, HttpServletRequest request)
        throws UnknownHostException{
        QueryWrapper<User> wrapper =new QueryWrapper<>();
        wrapper.eq("user_name",userName).eq("password",password);
        User user =userService.getOne(wrapper);//按照条件查询
        if(user == null){//通过用户名和密码查不到用户
            return ResponseResult.error("账号或密码错误！");
        }
        if(user.getIsLock()==1){//查出的用户is_lock属性为1，表示冻结
            return ResponseResult.error("账号已被冻结！");
        }
        if(user.getStudentStatus()==1){//student_status属性为1，表示不在籍
            return ResponseResult.error("账号不在籍！");
        }
        session.setAttribute("userId",user.getUserId());//将用户id存储在session中
        //获得用户的浏览器和ip地址信息
        Browser browser = UserAgent.parseUserAgentString(request.getHeader("User-Agent")).getBrowser();
        Version version =browser.getVersion(request.getHeader("User-Agent"));
        String browserInfo =browser.getName()+"/"+version.getVersion();
        String ip= InetAddress.getLocalHost().getHostAddress();
        //新增登录记录
        UserLoginRecord loginRecord=new UserLoginRecord();
        loginRecord.setBrowser(browserInfo);
        loginRecord.setIpAddr(ip);
        loginRecord.setLoginTime(LocalDateTime.now());
        loginRecord.setUserId(user.getUserId());
        userLoginRecordService.save(loginRecord);//向aoa_user_login_record插入登录日志
        //将用户id、实名、role_id、头像传入前端
        return ResponseResult.ok().put("userId",user.getUserId())
                .put("realName",user.getRealName())
                .put("className",classService.getById(user.getClassId()).getClassName())
                .put("roleId",user.getRoleId()).put("imgpath",user.getImgPath());
    }
  @GetMapping("/logout")
    public ResponseResult logout(HttpSession session){
        session.removeAttribute("userId");
        return ResponseResult.ok("成功退出登录！");
  }
}
