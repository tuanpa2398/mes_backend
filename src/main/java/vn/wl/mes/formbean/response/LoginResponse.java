package vn.wl.mes.formbean.response;

import lombok.Data;

@Data
public class LoginResponse {
	private Long id;
	private String username;
	private String role;
	private String first_name; 
    private String last_name;
    private String email; 
    private String avatar;
    private String plant;
    private String branch;
    private String[] permissions;
}
