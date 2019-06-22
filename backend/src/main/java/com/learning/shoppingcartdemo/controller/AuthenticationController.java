package com.learning.shoppingcartdemo.controller;

import com.learning.shoppingcartdemo.service.JWTUtil;
import com.learning.shoppingcartdemo.service.PBKDF2Encoder;
import com.learning.shoppingcartdemo.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

	private final JWTUtil jwtUtil;
	private final PBKDF2Encoder passwordEncoder;
	private final UserService userRepository;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Mono<ResponseEntity<?>> login(@RequestBody AuthRequest ar) {
		return userRepository.findByUsername(ar.getUsername()).map((userDetails) -> {
			if (passwordEncoder.encode(ar.getPassword()).equals(userDetails.getPassword())) {
				return ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(userDetails)));
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
		}).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

	@RequestMapping(value = "/login")
	public String login(){
		return "Login Here";
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	private static class  AuthRequest {
		private String username;
		private String password;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	private static class AuthResponse {
		private String token;
	}
}
