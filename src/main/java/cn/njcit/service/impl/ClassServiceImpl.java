package cn.njcit.service.impl;

import cn.njcit.entity.OlClass;
import cn.njcit.mapper.ClassMapper;
import cn.njcit.service.IClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 班级信息 服务实现类
 * </p>
 *
 * @author njcit
 * @since 2024-09-03
 */
@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, OlClass> implements IClassService {

}
