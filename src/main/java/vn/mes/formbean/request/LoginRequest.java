package vn.mes.formbean.request;

import lombok.Data;

@Data
public class LoginRequest {
	private String pattern;
	private String password;
}
