package cn.njcit.mapper;

import cn.njcit.entity.AttendClassSetting;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程时间设置 Mapper 接口
 * </p>
 *
 * @author mashiro
 * @since 2024-09-03
 */
public interface AttendClassSettingMapper extends BaseMapper<AttendClassSetting> {
    public AttendClassSetting selectByCourseId(long courseId);
    //根据courseID删除
    public int deleteByCourseId(Long courseId);


}
