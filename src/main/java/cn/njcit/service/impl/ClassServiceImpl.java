package cn.njcit.service.impl;

import cn.njcit.entity.OIClass;
import cn.njcit.mapper.ClassMapper;
import cn.njcit.service.IClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 班级信息 服务实现类
 * </p>
 *
 * @author mashiro
 * @since 2024-09-03
 */
@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, OIClass> implements IClassService {

    private final int PAGE_SIZE = 8;

    @Autowired
    private ClassMapper classMapper;

    @Override
    public PageInfo<OIClass> getOIClassList(Integer page, String className) {
        PageHelper.startPage(page,PAGE_SIZE);
        List<OIClass> oiClasses = null;
        if(className ==null||className.trim().equals("")){
            oiClasses = classMapper.getAllOIClass();
        }else{
            oiClasses = classMapper.searchOIClass(className);
        }
        return new PageInfo<OIClass>(oiClasses);
    }

    @Override
    public OIClass searchClassById(Integer id) {
        OIClass classById = classMapper.getClassById(id);
        return classById;
    }

    @Override
    public List<OIClass> getClassDouble() {
        List<OIClass> oiClasses = classMapper.getClassDouble();
        return oiClasses;
    }
}
