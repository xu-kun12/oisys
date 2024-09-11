package cn.njcit.controller;

import cn.njcit.entity.Course;
import cn.njcit.entity.OIClass;
import cn.njcit.entity.User;
import cn.njcit.service.ICourseService;
import cn.njcit.util.ResponseResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 课程信息 前端控制器
 * </p>
 *
 * @author mashiro
 * @since 2024-09-03
 */
@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private ICourseService courseService;

    @GetMapping("/searchCourse")
    public ResponseResult searchCourse(@RequestParam(defaultValue = "1")Integer page,
                                       @RequestParam(required = false)String courseName){
        PageInfo<Course> coursePageInfo = courseService.getCourseList(page,courseName);
        return ResponseResult.ok().put("courseList",coursePageInfo);
    }

    @PostMapping("/save")
    public ResponseResult saveCourse(Course course){
        if(course.getId() == null){
            QueryWrapper<Course> wrapper = new QueryWrapper<>();
           // System.out.println(course.getCourseNo());
            wrapper.eq("course_no",course.getCourseNo()).or().eq("course_name",course.getCourseName());
            if(courseService.count(wrapper)>0){
               // System.out.println(courseService.count(wrapper));
                return ResponseResult.error("课程编号和课程名字不能重复");
            }
        }
        course.setCreateTime(LocalDateTime.now()); // 设置修改时间
        return courseService.saveOrUpdate(course)?ResponseResult.ok("保存成功") : ResponseResult.error("保存失败");
    }

    @GetMapping("/edit")
    public ResponseResult courseEdit(@RequestParam(required = false) Long courseId){
     //   System.out.println(courseId);
        ResponseResult responseResult = ResponseResult.ok();
        if(courseId != null){
            Course course = courseService.getCourseById(courseId);
            responseResult.put("course",course);
        }
        return responseResult;
    }

    @DeleteMapping("/delete")
    public ResponseResult courseDelete(Long courseId){
        Course course = new Course();
        course.setId(courseId);
        return courseService.removeById(course) ? ResponseResult.ok("删除成功") : ResponseResult.error("删除失败");
    }
}
