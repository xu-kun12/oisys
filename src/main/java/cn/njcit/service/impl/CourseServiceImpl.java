package cn.njcit.service.impl;

import cn.njcit.entity.Course;
import cn.njcit.mapper.CourseMapper;
import cn.njcit.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程信息 服务实现类
 * </p>
 *
 * @author njcit
 * @since 2024-09-03
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

}
