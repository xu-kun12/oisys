package cn.njcit.mapper;

import cn.njcit.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author mashiro
 * @since 2024-09-03
 */
public interface UserMapper extends BaseMapper<User> {
    //查询所有学生
    public List<User> getAllStudents();

    //按照实名查询学生
    //模糊查询
    public List<User> searchStudents(String searchName);

    public User getStudentById(Long id);

    public List<User> getAllStudentsDouble();
}
