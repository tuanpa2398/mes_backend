package vn.wl.mes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.wl.mes.formbean.request.LoginRequest;
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
	
	@GetMapping("hello")
    public String sayHello() {
        return "Hello from Spring Boot!";
    }
	
	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest) {
		User user = userService.getUserByUsernameOrEmail(loginRequest.getPattern());
		
		if(user == null) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
}
