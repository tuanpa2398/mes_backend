package vn.mes.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import vn.mes.config.AppException;
import vn.mes.formbean.request.LoginRequest;
import vn.mes.formbean.response.ApiResponseDto;
import vn.mes.formbean.response.UserLoginResponseDto;
import vn.mes.model.user.User;
import vn.mes.service.user.UserService;
import vn.mes.util.JwtUtils;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/login")
	public ResponseEntity<ApiResponseDto> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
		ApiResponseDto res = new ApiResponseDto();
		
		User user = userService.getUserByUsernameOrEmail(loginRequest.getPattern());
		
		if(user == null) {			    
		    throw new AppException(401, "Sai thông tin đăng nhập.");
		}
		
		boolean verifyPassword = verifyPassword(loginRequest.getPassword(), user.getPassword());
		
		if(!verifyPassword) {
		    throw new AppException(401, "Sai thông tin đăng nhập.");
		}
		
		// Thông tin chính xác		
		String jwt = jwtUtils.generateToken(user);
		String refreshToken = jwtUtils.generateRefreshToken(user);
		
		UserLoginResponseDto loginResponse = new UserLoginResponseDto(user);
		
		res.setStatus(true);
	    res.setMessage("Đăng nhập thành công.");
	    res.setPath(request.getRequestURI());
	    res.getData().put("user", loginResponse);
	    res.getData().put("access_token", jwt);
	    res.getData().put("refresh_token", refreshToken);
		
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	
	@PostMapping("/refresh-token")
	public ResponseEntity<ApiResponseDto> refreshToken(@RequestBody Map<String, String> request, HttpServletRequest httpServletRequest) {
	    String refreshToken = request.get("refreshToken");
	    
	    ApiResponseDto res = new ApiResponseDto();
		res.setTimestamp(System.currentTimeMillis());
	    
		jwtUtils.validateToken(refreshToken);
    	
    	if (!"refresh".equals(jwtUtils.extractTokenType(refreshToken))) {
    		res.setStatus(false);
			return new ResponseEntity<>(res, HttpStatus.OK);
	    }

	    String username = jwtUtils.extractUsername(refreshToken);

	    User user = userService.getUserByUsernameOrEmail(username);
	    
		if(user == null) {
			res.setStatus(false);
			res.setMessage("Không tìm thấy người dùng.");
			return new ResponseEntity<>(res, HttpStatus.OK);
		}

		// Thông tin chính xác		
		String jwt = jwtUtils.generateToken(user);
		
		res.setStatus(true);
	    res.setMessage("Đăng nhập thành công.");
	    res.setPath(httpServletRequest.getRequestURI());
	    res.getData().put("access_token", jwt);
		
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	private boolean verifyPassword(String rawPassword, String encodedPasswordFromDb) {
	    return passwordEncoder.matches(rawPassword, encodedPasswordFromDb);
	}
}
