package vn.wl.mes.controller.mes;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.wl.mes.formbean.response.LoginResponse;
import vn.wl.mes.model.user.User;
import vn.wl.mes.service.user.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/get-current-user")
	public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserDetails user){
		Map<String, Object> response = new HashMap<>();
		
		if(user == null) {
			response.put("status", false);
			response.put("message", "Không tìm thấy User hệ thống");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		
		User u = userService.getUserByUsernameOrEmail(user.getUsername());
		
		LoginResponse loginResponse = new LoginResponse(u);
		
		response.put("status", true);
		response.put("message", "Lấy thông tin Người dùng hệ thống thành công!!.");
		response.put("user", loginResponse);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
