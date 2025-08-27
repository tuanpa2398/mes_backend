package vn.wl.mes.service.user;

import vn.wl.mes.model.user.User;

public interface UserService {
	public User getUserByUsernameOrEmail(String pattern);
}
