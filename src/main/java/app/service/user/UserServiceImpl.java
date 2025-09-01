package app.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.mes.user.User;
import app.mapper.postgre.UserMapper;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public User getUserByUsernameOrEmail(String pattern) {
		
		return userMapper.getUserByUsernameOrEmail(pattern);
	}

}
