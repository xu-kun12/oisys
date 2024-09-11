package cn.njcit.mapper;

import cn.njcit.entity.CourseManage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 课程管理表 Mapper 接口
 * </p>
 *
 * @author mashiro
 * @since 2024-09-03
 */
public interface CourseManageMapper extends BaseMapper<CourseManage> {
    public List<CourseManage> getAllCourseManage();

    public List<CourseManage> searchCourseManageName(String courseManageName);

    public CourseManage getCourseManageById(Long id);

    public List<CourseManage> getAttendanceRecordsByName(String teacherName,String month);
}
