package cn.njcit.controller;

import cn.njcit.entity.*;
import cn.njcit.service.*;
import cn.njcit.util.ResponseResult;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程管理表 前端控制器
 * </p>
 *
 * @author mashiro
 * @since 2024-09-03
 */
@RestController
@RequestMapping("/courseManage")
public class CourseManageController {

    @Autowired
    private ICourseManageService courseManageService;

    @Autowired
    private IStudentAttendanceService iStudentAttendanceService;

    @Autowired
    private IAttendClassSettingService iAttendClassSettingService;

    @Autowired
    private ICourseService iCourseService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IClassService classService;

    @GetMapping("/list")
    public ResponseResult searchCourseManage(@RequestParam(defaultValue = "1") Integer page,
                                              @RequestParam(required = false)String courseManageName){
        PageInfo<CourseManage> courseManagePageInfo = courseManageService.getCourseManageList(page,courseManageName);
        System.out.println(courseManagePageInfo);
        return ResponseResult.ok().put("courseManageList",courseManagePageInfo);
    }

    @GetMapping("/edit")
    public ResponseResult saveCourseManageGet(@RequestParam(required = false)Long id){
        CourseManage courseManageById = null;
        if(id!=null){
            courseManageById = courseManageService.getCourseManageById(id);
        }
        List<Course> courseList = iCourseService.getAllCoursesDouble();
        List<User> userList = userService.getAllStudentsDouble();
        List<OIClass> oiClasses = classService.getClassDouble();

        CourseManageDouble courseManageDoubleList = new CourseManageDouble(courseManageById,courseList,userList,oiClasses);
        return ResponseResult.ok().put("courseManageSave",courseManageDoubleList);
    }

    /*调试*/
    /*
    * 调试语句
    * postman
    *
{
  "courseManage": {
    "id": 69,
    "courseNo": "003",
    "courseName": "Class A",
    "teacherNo": "003",
    "teacherName": "Class A",
    "classNo": "003",
    "className": "Class A"
  },
  "attendClassSettings": [
    {
      "id": 280,
      "attendClassWeek": "周二",
      "startTime": "8:00",
      "endTime": "8:15",
      "address": "咨询了"
    },
    {
      "id": 281,
      "attendClassWeek": "周二",
      "startTime": "8:00",
      "endTime": "8:15",
      "address": "咨询了"
    },
    {
      "id": "",
      "attendClassWeek": "周二",
      "startTime": "8:00",
      "endTime": "8:15",
      "address": "咨询"
    }
  ]
}

    * */
    @PostMapping("/save")
    public ResponseResult saveCourseManage(@RequestBody CourseManageRequest request){

        CourseManage courseManage = request.getCourseManage();
        List<AttendClassSetting> attendClassSettings = request.getAttendClassSettings();

        System.out.println(courseManage);
        for (AttendClassSetting setting:attendClassSettings){
            System.out.println(setting);
        }
        System.out.println(attendClassSettings);

        if(courseManage.getId() == null){
            courseManage.setCreateTime(LocalDateTime.now());
        }
        courseManage.setUpdateTime(LocalDateTime.now());
        boolean courseManageSave = courseManageService.saveOrUpdate(courseManage);
        boolean attendClassSave = false;
        if(courseManageSave){
            Long attendClassCourseId = courseManage.getId();
            for (AttendClassSetting setting : attendClassSettings){
                if(setting.getId()==null){
                    setting.setCourseId(attendClassCourseId);
                }
            }
            attendClassSave = iAttendClassSettingService.saveOrUpdateBatch(attendClassSettings);
        }
        if(courseManageSave && attendClassSave){
            return ResponseResult.ok("保存成功！");
        }else{
            return ResponseResult.error("保存失败！");
        }
    }

    @DeleteMapping("/delete")
    public ResponseResult deleteCourseManage(@RequestParam(required = false) Long id,@RequestParam(required = false) Long courseId){
        if(id!=null){
            CourseManage courseManage = new CourseManage();
            int attendClassDelete = iStudentAttendanceService.deleteByCourseId(id);
            courseManage.setId(id);
         //   System.out.println(courseManage);
            boolean courseManageDelete = courseManageService.removeById(courseManage);
            if(attendClassDelete!=0 && courseManageDelete){
                return ResponseResult.ok("删除成功!");
            }else{
                return ResponseResult.error("删除失败!");
            }
        }else if(courseId != null){
            AttendClassSetting attendClassSetting = new AttendClassSetting();
            attendClassSetting.setId(courseId);
            return iAttendClassSettingService.removeById(attendClassSetting)?ResponseResult.ok("删除成功"):ResponseResult.error("删除失败");
        }else{
            return ResponseResult.ok("删除错误");
        }
    }
}
