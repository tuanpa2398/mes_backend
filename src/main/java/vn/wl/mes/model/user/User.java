package vn.wl.mes.model.user;

import lombok.Data;

@Data
public class User {
	private Long id;
	private String username;
	private String password;
	private String role;
}
