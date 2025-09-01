package vn.mes.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.mes.mapper.postgre.UserMapper;
import vn.mes.model.user.User;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public User getUserByUsernameOrEmail(String pattern) {
		
		return userMapper.getUserByUsernameOrEmail(pattern);
	}

}
