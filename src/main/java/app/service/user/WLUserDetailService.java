package app.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import app.domain.mes.user.User;
import app.mapper.postgre.UserMapper;

@Service
public class WLUserDetailService implements UserDetailsService {
	@Autowired
	private UserMapper userMapper;

	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userMapper.getUserByUsername(username);
		return org.springframework.security.core.userdetails.User.builder().username(user.getUsername())
				.password(user.getPassword()) 
				.roles(user.getRole())
				.build();
	}

}
