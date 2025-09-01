package app.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.configs.AppException;
import app.domain.mes.auth.AppCurrentUser;
import app.domain.mes.auth.LoginDto;
import app.domain.mes.auth.LoginRequest;
import app.domain.mes.auth.RefreshTokenResponseDto;
import app.domain.mes.user.User;
import app.service.user.UserService;
import app.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;

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
	public LoginDto login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
		User user = userService.getUserByUsernameOrEmail(loginRequest.getPattern());
		
		if(user == null) {			    
		    throw new AppException(401, "Sai thông tin đăng nhập.");
		}
		
		boolean verifyPassword = verifyPassword(loginRequest.getPassword(), user.getPassword());
		
		if(!verifyPassword) {
		    throw new AppException(401, "Sai thông tin đăng nhập.");
		}
		
		if(user.getIs_block() == 1) {
			throw new AppException(401, "Tài khoản bị khóa. Liên hệ Adminstrator.");
		}
		
		// Thông tin chính xác		
		String jwt = jwtUtils.generateToken(user);
		String refreshToken = jwtUtils.generateRefreshToken(user);
		
		AppCurrentUser appCurrentUser = new AppCurrentUser(user);
		
		LoginDto loginDto = new LoginDto(appCurrentUser, jwt, refreshToken);
		
		return loginDto;
	}
	
	
	@PostMapping("/refresh-token")
	public RefreshTokenResponseDto refreshToken(@RequestBody Map<String, String> request, HttpServletRequest httpServletRequest) {
	    String refreshToken = request.get("refreshToken");
	    
	    try {
	    	jwtUtils.validateToken(refreshToken);
	    	
	    	if (!"refresh".equals(jwtUtils.extractTokenType(refreshToken))) {
	    		throw new AppException(401, "Phiên đăng nhập hết hạn. Vui lòng đăng nhập lại.");
		    }

		    String username = jwtUtils.extractUsername(refreshToken);

		    User user = userService.getUserByUsernameOrEmail(username);
		    
			if(user == null) {
				throw new AppException(401, "Phiên đăng nhập hết hạn. Vui lòng đăng nhập lại.");
			}
			
			if(user.getIs_block() == 1) {
				throw new AppException(401, "Tài khoản bị khóa. Liên hệ Adminstrator.");
			}
			
			// Thông tin chính xác		
			String jwt = jwtUtils.generateToken(user);
			RefreshTokenResponseDto refreshTokenResponseDto = new RefreshTokenResponseDto(jwt);
			return refreshTokenResponseDto;
		} catch (io.jsonwebtoken.ExpiredJwtException e) {
			throw new AppException(401, "Phiên đăng nhập hết hạn. Vui lòng đăng nhập lại.");
		}  catch (Exception e) {
			throw new AppException(401, "Phiên đăng nhập hết hạn. Vui lòng đăng nhập lại.");
		}
	}
	
	private boolean verifyPassword(String rawPassword, String encodedPasswordFromDb) {
	    return passwordEncoder.matches(rawPassword, encodedPasswordFromDb);
	}
}
