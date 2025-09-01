package app.service.user;

import app.domain.mes.user.User;

public interface UserService {
	public User getUserByUsernameOrEmail(String pattern);
}
