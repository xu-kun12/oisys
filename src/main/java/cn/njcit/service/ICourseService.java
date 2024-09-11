package cn.njcit.service;

import cn.njcit.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 课程信息 服务类
 * </p>
 *
 * @author mashiro
 * @since 2024-09-03
 */
public interface ICourseService extends IService<Course> {
    public PageInfo<Course> getCourseList(Integer page,String courseName);
    public Course getCourseById(Long courseId);
    public List<Course> getAllCoursesDouble();
}
