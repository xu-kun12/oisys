package cn.njcit.service.impl;

import cn.njcit.entity.User;
import cn.njcit.mapper.UserMapper;
import cn.njcit.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author njcit
 * @since 2024-09-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
 private final int PAGE_SIZE=5;
    @Override
    public PageInfo<User> getStudentList(Integer page, String searchName) {
        PageHelper.startPage(page,PAGE_SIZE);
        List<User> studentList=null;
        if(searchName==null||searchName.trim().equals("")){
            studentList=baseMapper.getAllStudents();
        }else {
            studentList=baseMapper.searchStudents(searchName);
        }
        return new PageInfo<User>(studentList);
    }
    @Override
    public User getStudentById(Long id){
        User student = baseMapper.getStudentById(id);
        return student;
    }
}
