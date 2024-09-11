package cn.njcit.service.impl;

import cn.njcit.entity.Course;
import cn.njcit.entity.OIClass;
import cn.njcit.mapper.CourseMapper;
import cn.njcit.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程信息 服务实现类
 * </p>
 *
 * @author mashiro
 * @since 2024-09-03
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    private final int PAGE_SIZE = 10;

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public PageInfo<Course> getCourseList(Integer page, String courseName) {
        PageHelper.startPage(page,PAGE_SIZE);
        List<Course> courseList = null;
        if(courseName == null || courseName.trim().equals("")){
            courseList = courseMapper.getAllCourses();
        }else{
            courseList = courseMapper.searchCourseNoName(courseName);
        }
        return new PageInfo<Course>(courseList);
    }

    @Override
    public Course getCourseById(Long courseId) {
        Course courseById = courseMapper.getCourseById(courseId);
        return courseById;
    }

    @Override
    public List<Course> getAllCoursesDouble() {
        List<Course> courseList = courseMapper.getAllCoursesDouble();
        return courseList;
    }
}
