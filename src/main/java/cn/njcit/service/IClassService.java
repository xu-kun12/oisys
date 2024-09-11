package cn.njcit.service;

import cn.njcit.entity.OIClass;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 班级信息 服务类
 * </p>
 *
 * @author mashiro
 * @since 2024-09-03
 */
public interface IClassService extends IService<OIClass> {
    public PageInfo<OIClass> getOIClassList(Integer page,String className);
    public OIClass searchClassById(Integer id);
    public List<OIClass> getClassDouble();
}
