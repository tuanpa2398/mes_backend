package vn.wl.mes.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AuthController {
	
	@GetMapping("hello")
    public String sayHello() {
        return "Hello from Spring Boot!";
    }
}
