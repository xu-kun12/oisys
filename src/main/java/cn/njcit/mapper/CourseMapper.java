package cn.njcit.mapper;

import cn.njcit.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 课程信息 Mapper 接口
 * </p>
 *
 * @author mashiro
 * @since 2024-09-03
 */
public interface CourseMapper extends BaseMapper<Course> {
    //查询所有课程
    public List<Course> getAllCourses();

    //按照编号和名称模糊查询
    public List<Course> searchCourseNoName(String courseName);

    public Course getCourseById(long courseId);

    public List<Course> getAllCoursesDouble();

}
