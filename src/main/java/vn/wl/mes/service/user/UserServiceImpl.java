package vn.wl.mes.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.wl.mes.mapper.UserMapper;
import vn.wl.mes.model.user.User;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public User getUserByUsernameOrEmail(String pattern) {
		
		return userMapper.getUserByUsernameOrEmail(pattern);
	}

}
