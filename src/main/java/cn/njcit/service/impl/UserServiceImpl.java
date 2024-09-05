package cn.njcit.service.impl;

import cn.njcit.entity.User;
import cn.njcit.mapper.UserMapper;
import cn.njcit.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author njcit
 * @since 2024-09-02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private final int PAGE_SIZE = 5;//每页展示条数
    @Value("${userImageRoot}")
    private String userImageRoot;
    /**
     *
     */
    @Override
    public PageInfo<User> getStudentList(Integer page,String searchName){
        PageHelper.startPage(page,PAGE_SIZE);//设置起始页，
        List<User> studentList = null;
        if (searchName == null || searchName.trim().equals("")) {//searchName为null或空字符串，查询所有学生
            studentList = baseMapper.getAllStudents();
        }else {//searchName不为null，按照实名查询学生
            studentList = baseMapper.searchStudents(searchName);
        }
        return new PageInfo<User>(studentList);//将查询到的学生列表包装成分页对象
    }
    @Override
    public User getStudentById(Long id){
        User student = baseMapper.getStudentById(id);
        return student;
    }

    /**
     *
     */
    public String upload(MultipartFile file) throws IllegalStateException, IOException {
        if (file == null) {
            return null;
        }
        File dir = new File(userImageRoot);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String fileName = file.getOriginalFilename();
        if (!StringUtil.isEmpty(fileName)) {
            String suffix = FilenameUtils.getExtension(fileName);
            String newFileName = UUID.randomUUID().toString().toLowerCase() + "." +suffix;
            File targetFile = new File(dir,newFileName);
            file.transferTo(targetFile);
            return newFileName;
        }else {
            return null;
        }
    }
}
