package cn.njcit.controller;

import cn.njcit.entity.CourseManage;
import cn.njcit.entity.StudentAttendance;
import cn.njcit.entity.StudentClock;
import cn.njcit.service.ICourseManageService;
import cn.njcit.service.ICourseService;
import cn.njcit.service.IStudentAttendanceService;
import cn.njcit.service.IStudentClockService;
import cn.njcit.util.ResponseResult;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * <p>
 * 考勤打卡 前端控制器
 * </p>
 *
 * @author mashiro
 * @since 2024-09-03
 */
@RestController
@RequestMapping("/attendsList")
public class AttendsListController {

    @Autowired
    private ICourseManageService courseManageService;

    @Autowired
    private IStudentClockService studentClockService;

    @Autowired
    private IStudentAttendanceService iStudentAttendanceService;

    //  查询老师当月授课情况   前端给时间  返回有id注意
    @GetMapping("list")
    public ResponseResult searchAttendsList(@RequestParam(required = false) String realName,
                                            @RequestParam(required = false) String month){//月份参数（yyy-MM）){
      //  String teacherName = (String) session.getAttribute("userName");
        String teacherName = realName;

        System.out.println(month);
        System.out.println(realName);

        if(teacherName == null){
            return ResponseResult.error("用户未登录");
        }
       // LocalDateTime now = LocalDateTime.now();
       // String yearMonth = now.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        String yearMonth = "2022-10";

        if (month == null || month.isEmpty()) {
            month = yearMonth;
        }

//        if(month.compareTo(yearMonth)>0){
//            return ResponseResult.error("cnm，错误，过多了 ...1..");
//        }

       // System.out.println(teacherName);
        List<CourseManage> courseManageList = courseManageService.getAttendanceRecordsForTeacher(teacherName,month);
        return ResponseResult.ok().put("attendsCourseList",courseManageList);
    }

    //-- 查询老师单课程的学生打卡情况    后端接收前端传来的id进行多表查询  id为aoa_course_manage的id  可选择传 realName进行模糊查询
    @GetMapping("/studentClockList")
    public ResponseResult searchStudentList(Long id,
                                             @RequestParam(defaultValue = "1")Integer page,
                                             @RequestParam(required = false)String realName){
        if(id==0){
            return ResponseResult.ok("无法查询");
        }
        PageInfo<StudentClock> studentClockPageInfo = studentClockService.getStudentClock(page,id,realName);
        return ResponseResult.ok().put("studentClockList",studentClockPageInfo);
    }

    // -- 查询学生考勤情况   前端传courseId  和 attendsUserId        前端传course_id  和 attends_user_id
    @GetMapping("studentAttendance")
    public ResponseResult searchStudentAttendance(@RequestParam(defaultValue = "1") Integer page, Long courseId,Long attendsUserId){
        PageInfo<StudentAttendance> studentAttendancePageInfo = iStudentAttendanceService.getStudentAttendance(page,courseId,attendsUserId);
        return ResponseResult.ok().put("studentAttendanceList",studentAttendancePageInfo);
    }
}
