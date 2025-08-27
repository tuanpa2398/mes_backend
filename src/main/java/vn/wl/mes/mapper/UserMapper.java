package vn.wl.mes.mapper;

import org.apache.ibatis.annotations.Mapper;

import vn.wl.mes.model.user.User;

@Mapper
public interface UserMapper {
	User getUserByUsernameOrEmail(String param);
	User getUserByUsername(String username);
	User getUserById(String id);
}
