package cn.njcit.service;

import cn.njcit.entity.Course;
import cn.njcit.entity.CourseManage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 课程管理表 服务类
 * </p>
 *
 * @author mashiro
 * @since 2024-09-03
 */
public interface ICourseManageService extends IService<CourseManage> {
    public PageInfo<CourseManage> getCourseManageList(Integer page, String courseManageName);

    public CourseManage getCourseManageById(Long id);

    public List<CourseManage> getAttendanceRecordsForTeacher(String teacherName,String month);
}
