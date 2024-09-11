package cn.njcit.mapper;

import cn.njcit.entity.OIClass;
import cn.njcit.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 班级信息 Mapper 接口
 * </p>
 *
 * @author mashiro
 * @since 2024-09-03
 */
public interface ClassMapper extends BaseMapper<OIClass> {
     //查询所有班级
    public List<OIClass> getAllOIClass();

    //模糊查询
    public List<OIClass> searchOIClass(String className);
    public OIClass getClassById(Integer id);

    public List<OIClass> getClassDouble();
}
