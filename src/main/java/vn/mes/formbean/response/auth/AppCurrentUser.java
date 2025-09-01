package vn.mes.formbean.response.auth;

import lombok.Data;
import vn.mes.model.user.User;

@Data
public class AppCurrentUser {
	private Long id;
	private String role;
	private String first_name; 
    private String last_name;
    private String email; 
    private String avatar;
    private String plant;
    private String branch;
    private String[] permissions;
    
    public AppCurrentUser(User u) {
    	this.setId(u.getId());
    	this.setRole(u.getRole());
    	this.setFirst_name(u.getFirst_name());
    	this.setLast_name(u.getLast_name());
    	this.setEmail(u.getEmail());
    	this.setPlant(u.getPlant());
    	this.setBranch(u.getBranch());
    }
}
