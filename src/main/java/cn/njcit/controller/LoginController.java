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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/")
public class LoginController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IUserLoginRecordService userLoginRecordService;

    @Autowired
    private IClassService classService;

    @PostMapping(value = "login")
    public ResponseResult loginCheck(String userName, String password, HttpSession session, HttpServletRequest request) throws UnknownHostException{

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",userName).eq("password",password);
        User user = userService.getOne(wrapper);
        if(user == null){
            return ResponseResult.error("账号密码错误！");
        }
        if(user.getIsLock()==1){
            return ResponseResult.error("账号已被冻结！");
        }
        if(user.getStudentStatus()==1){
            return ResponseResult.error("账号不在籍！");
        }
        session.setAttribute("userId",user.getUserId());
        session.setAttribute("userName",user.getRealName());

        Browser browser = UserAgent.parseUserAgentString(request.getHeader("User-Agent")).getBrowser();
        Version version = browser.getVersion(request.getHeader("User-Agent"));

        String browserInfo;
        if (version != null) {
            browserInfo = browser.getName() + "/" + version.getVersion();
        } else {
            browserInfo = browser.getName() + "/Unknown"; // 版本信息不知道
        }

        String ip = InetAddress.getLocalHost().getHostAddress();


//        Browser browser = UserAgent.parseUserAgentString(request.getHeader("User-Agent")).getBrowser();
//        Version version = browser.getVersion(request.getHeader("User-Agent"));
//        String browserInfo = browser.getName() + "/" + version.getVersion();
//        String ip = InetAddress.getLocalHost().getHostAddress();

        UserLoginRecord loginRecord = new UserLoginRecord();
        loginRecord.setBrowser(browserInfo);
        loginRecord.setIpAddr(ip);
        loginRecord.setLoginTime(LocalDateTime.now());
        loginRecord.setUserId(user.getUserId());
        System.out.println(loginRecord);
        userLoginRecordService.save(loginRecord);

        return ResponseResult.ok().put("userId",user.getUserId())
                .put("userName",user.getUserName())
                .put("realName",user.getRealName())
                .put("className",classService.getById(user.getClassId()).getClassName())
                .put("roleId",user.getRoleId()).put("imgPath",user.getImgPath());
    }

    @GetMapping("logout")
    public ResponseResult logout(HttpSession session){
        session.removeAttribute("userId");
        return ResponseResult.ok("成功退出登录");
    }


}
