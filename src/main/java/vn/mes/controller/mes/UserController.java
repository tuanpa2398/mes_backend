package vn.mes.controller.mes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import vn.mes.formbean.response.ApiResponseDto;
import vn.mes.formbean.response.UserLoginResponseDto;
import vn.mes.model.user.User;
import vn.mes.service.user.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/get-current-user")
	public ResponseEntity<ApiResponseDto> getCurrentUser(@AuthenticationPrincipal UserDetails user, HttpServletRequest httpServletRequest){		
		ApiResponseDto res = new ApiResponseDto();
		
		if(user == null) {
			res.setStatus(false);
			res.setMessage("Không tìm thấy User hệ thống");
			return new ResponseEntity<>(res, HttpStatus.OK);
		}
		
		User u = userService.getUserByUsernameOrEmail(user.getUsername());
		
		if(u == null) {			    
		    res.setStatus(false);
		    res.setMessage("Lấy thông tin Người dùng hệ thống thất bại.");
			return new ResponseEntity<>(res, HttpStatus.OK);
		}
		
		UserLoginResponseDto loginResponse = new UserLoginResponseDto(u);
		
		res.setStatus(true);
		res.setMessage("Lấy thông tin Người dùng hệ thống thành công!!.");
		res.setPath(httpServletRequest.getRequestURI());
		res.getData().put("user", loginResponse);
		
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
