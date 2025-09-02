package app.domain.mes.user;

import lombok.Data;

@Data
public class UserListDto {
	private Long id;
	private String first_name; 
    private String last_name;
    private String username;
    private String plant;
    private String sap_id;
    private Integer is_block = 0;
    private String branch;
}
