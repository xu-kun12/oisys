package cn.njcit.service;

import cn.njcit.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mashiro
 * @since 2024-09-03
 */
public interface IUserService extends IService<User> {
    public PageInfo<User> getStudentList(Integer page,String searchName);
    public User getStudentById(Long id);
    public String upload(MultipartFile file) throws IllegalStateException, IOException;
    public List<User> getAllStudentsDouble();
}
