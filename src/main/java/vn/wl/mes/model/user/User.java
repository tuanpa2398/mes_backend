package vn.wl.mes.model.user;

import lombok.Data;

@Data
public class User {
	private Long id;
	private String username;
	private String password;
	private String role;
	private String first_name; 
    private String last_name;
    private String gender;
    private String email; 
    private String avatar;
    private String plant;
    private String sap_id;
    private String integration_id;
    private String other_info;
    private String branch;
    private Integer is_block = 0; 
    private String email_verified_at; 
    private String remember_token;
    private String created_at;
    private String updated_at;
    private String imagesign;
}
