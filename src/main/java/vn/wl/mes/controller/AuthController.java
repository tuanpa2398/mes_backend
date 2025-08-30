package vn.wl.mes.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.wl.mes.formbean.request.LoginRequest;
import vn.wl.mes.formbean.response.LoginResponse;
import vn.wl.mes.model.user.User;
import vn.wl.mes.service.user.UserService;
import vn.wl.mes.util.JwtUtils;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("hello")
    public String sayHello() {
        return "Hello from Spring Boot!";
    }
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		User user = userService.getUserByUsernameOrEmail(loginRequest.getPattern());
		
		Map<String, Object> response = new HashMap<>();
		
		if(user == null) {
		    response.put("status", false);
		    response.put("message", "Sai thông tin đăng nhập.");
		    response.put("timestamp", System.currentTimeMillis());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		
		if(!verifyPassword(loginRequest.getPassword(), user.getPassword())) {
			response.put("status", false);
		    response.put("message", "Mật khẩu không chính xác.");
		    response.put("timestamp", System.currentTimeMillis());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		
		
		// Thông tin chính xác		
		String jwt = jwtUtils.generateToken(user);
		String refreshToken = jwtUtils.generateRefreshToken(user);
		
		LoginResponse loginResponse = new LoginResponse(user);
		
		response.put("status", true);
		response.put("message", "Đăng nhập thành công.");
		response.put("user", loginResponse);
		response.put("access_token", jwt);
		response.put("refresh_token", refreshToken);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/refresh-token")
	public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
	    String refreshToken = request.get("refreshToken");
	    
	    Map<String, Object> response = new HashMap<>();
	    
	    try {
	    	jwtUtils.validateToken(refreshToken);
	    	
	    	if (!"refresh".equals(jwtUtils.extractTokenType(refreshToken))) {
	    		response.put("status", false);
				return new ResponseEntity<>(response, HttpStatus.OK);
		    }

		    String username = jwtUtils.extractUsername(refreshToken);

		    User user = userService.getUserByUsernameOrEmail(username);
		    
			if(user == null) {
			    response.put("status", false);
			    response.put("message", "Không tìm thấy người dùng.");
			    response.put("timestamp", System.currentTimeMillis());
				return new ResponseEntity<>(response, HttpStatus.OK);
			}

		 // Thông tin chính xác		
			String jwt = jwtUtils.generateToken(user);
			
			response.put("status", true);
			response.put("access_token", jwt);
			
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.put("status", false);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}
	
	private boolean verifyPassword(String rawPassword, String encodedPasswordFromDb) {
	    return passwordEncoder.matches(rawPassword, encodedPasswordFromDb);
	}
}
