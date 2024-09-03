package cn.njcit.service.impl;

import cn.njcit.entity.User;
import cn.njcit.mapper.UserMapper;
import cn.njcit.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
