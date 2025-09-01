package app.controllers.mes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.configs.AppException;
import app.domain.mes.auth.AppCurrentUser;
import app.domain.mes.user.User;
import app.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/get-current-user")
	public AppCurrentUser getCurrentUser(@AuthenticationPrincipal UserDetails userDetails, HttpServletRequest httpServletRequest){		
		if(userDetails == null) {
			throw new AppException(401, "Phiên đăng nhập hết hạn. Vui lòng đăng nhập lại.", "TOKEN_INVALID");
		}
		
		User user = userService.getUserByUsernameOrEmail(userDetails.getUsername());
		
		if(user == null) {			    
			throw new AppException(401, "Phiên đăng nhập hết hạn. Vui lòng đăng nhập lại.", "TOKEN_INVALID");
		}
		
		AppCurrentUser appCurrentUser = new AppCurrentUser(user);
		
		return appCurrentUser;
	}
}
