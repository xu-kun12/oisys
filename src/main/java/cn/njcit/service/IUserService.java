package cn.njcit.service;

import cn.njcit.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author njcit
 * @since 2024-09-03
 */
public interface IUserService extends IService<User> {
    public PageInfo<User> getStudentList(Integer page,String searchName);

}
