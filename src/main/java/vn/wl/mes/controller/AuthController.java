package vn.wl.mes.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.wl.mes.formbean.LoginRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@GetMapping("hello")
    public String sayHello() {
        return "Hello from Spring Boot!";
    }
	
	@PostMapping("/login")
	public void login(LoginRequest loginRequest) {
		
	}
}
