package app.domain.mes.auth;

import lombok.Data;

@Data
public class LoginRequest {
	private String pattern;
	private String password;
}
