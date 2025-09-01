package vn.mes.formbean.response;

import lombok.Data;
import vn.mes.model.user.User;

@Data
public class UserLoginResponseDto {
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
    
    public UserLoginResponseDto(User u) {
    	this.setId(u.getId());
    	this.setUsername(u.getUsername());
    	this.setRole(u.getRole());
    	this.setFirst_name(u.getFirst_name());
    	this.setLast_name(u.getLast_name());
    	this.setEmail(u.getEmail());
    	this.setPlant(u.getPlant());
    	this.setBranch(u.getBranch());
    }
}
