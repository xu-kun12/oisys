package cn.njcit.service.impl;

import cn.njcit.entity.User;
import cn.njcit.mapper.UserMapper;
import cn.njcit.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mashiro
 * @since 2024-09-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final int PAGE_SIZE = 5;//每页5条数据

    @Value("${userImageRoot}")
    private String userImageRoot;

    @Autowired
    private UserMapper userMapper;

    @Override
    public PageInfo<User> getStudentList(Integer page, String searchName) {

        PageHelper.startPage(page,PAGE_SIZE);
        List<User> studentList = null;
        if(searchName == null || searchName.trim().equals("")){
            studentList = baseMapper.getAllStudents();
        }else{
            studentList = userMapper.searchStudents(searchName);
        }
        return new PageInfo<User>(studentList);
    }

    @Override
    public User getStudentById(Long id) {
        User student = baseMapper.getStudentById(id);
        return student;
    }

    @Override
    public String upload(MultipartFile file) throws IllegalStateException, IOException {
        if (file == null){
            return null;
        }
        File dir = new File(userImageRoot);
        if(!dir.exists()){
            dir.mkdir();
        }
        String fileName = file.getOriginalFilename();
        if(!StringUtil.isEmpty(fileName)){
            String suffix = FilenameUtils.getExtension(fileName);
            String newFileName = UUID.randomUUID().toString().toLowerCase() + "." + suffix;
            File targetFile = new File(dir, newFileName);
            file.transferTo(targetFile);
            return newFileName;
        }else{
            return null;
        }
    }

    @Override
    public List<User> getAllStudentsDouble() {
        List<User> userList = userMapper.getAllStudentsDouble();
        return userList;
    }
}
