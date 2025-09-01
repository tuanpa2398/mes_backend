package vn.mes.formbean.response;

import lombok.Data;
import vn.mes.formbean.response.auth.AppCurrentUser;

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
