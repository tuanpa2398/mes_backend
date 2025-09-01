package app.mapper.postgre;

import org.apache.ibatis.annotations.Mapper;

import app.domain.mes.user.User;

@Mapper
public interface UserMapper {
	User getUserByUsernameOrEmail(String param);
	User getUserByUsername(String username);
	User getUserById(String id);
}
