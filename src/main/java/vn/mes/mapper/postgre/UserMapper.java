package vn.mes.mapper.postgre;

import org.apache.ibatis.annotations.Mapper;

import vn.mes.model.user.User;

@Mapper
public interface UserMapper {
	User getUserByUsernameOrEmail(String param);
	User getUserByUsername(String username);
	User getUserById(String id);
}
