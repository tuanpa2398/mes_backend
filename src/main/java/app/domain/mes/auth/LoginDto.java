package app.domain.mes.auth;

import lombok.Data;

@Data
public class LoginDto {
	private AppCurrentUser appCurrentUser;
	private String accessToken;
	private String refreshToken;
	
	
	public LoginDto(AppCurrentUser appCurrentUser, String accessToken, String refreshToken) {
		this.appCurrentUser = appCurrentUser;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
}
