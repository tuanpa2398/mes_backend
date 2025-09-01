package vn.mes.service.user;

import vn.mes.model.user.User;

public interface UserService {
	public User getUserByUsernameOrEmail(String pattern);
}
