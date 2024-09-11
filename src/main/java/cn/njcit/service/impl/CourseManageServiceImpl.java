package cn.njcit.service.impl;

import cn.njcit.entity.CourseManage;
import cn.njcit.mapper.CourseManageMapper;
import cn.njcit.service.ICourseManageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程管理表 服务实现类
 * </p>
 *
 * @author mashiro
 * @since 2024-09-03
 */
@Service
public class CourseManageServiceImpl extends ServiceImpl<CourseManageMapper, CourseManage> implements ICourseManageService {

    private final int PAGE_SIZE = 5;

    @Autowired
    private CourseManageMapper courseManageMapper;

    @Override
    public PageInfo<CourseManage> getCourseManageList(Integer page, String courseManageName) {
        PageHelper.startPage(page,PAGE_SIZE);
        List<CourseManage> courseManageList = null;
        if(courseManageName == null || courseManageName.trim().equals("")){
            courseManageList = courseManageMapper.getAllCourseManage();
        }else{
            courseManageList = courseManageMapper.searchCourseManageName(courseManageName);
        }
        return new PageInfo<>(courseManageList);
    }

    @Override
    public CourseManage getCourseManageById(Long id) {
        CourseManage courseManage = courseManageMapper.getCourseManageById(id);
        return courseManage;
    }

    @Override
    public List<CourseManage> getAttendanceRecordsForTeacher(String teacherName,String month) {
        List<CourseManage> courseManageList = courseManageMapper.getAttendanceRecordsByName(teacherName,month);
        return courseManageList;
    }
}
