package org.example.dailydiary.user.controller;

import org.example.dailydiary.user.dto.request.CreateUserRequestDto;
import org.example.dailydiary.user.dto.request.LoginUserRequestDto;
import org.example.dailydiary.user.dto.response.CreateUserResponseDto;
import org.example.dailydiary.user.dto.response.LoginUserResponseDto;
import org.example.dailydiary.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<CreateUserResponseDto> createUser(
		@RequestBody CreateUserRequestDto requestDto
	) {
		CreateUserResponseDto responseDto = userService.saveUser(requestDto);

		return ResponseEntity.status(HttpStatus.CREATED)
			.body(responseDto);
	}

	@PostMapping("/login")
	public ResponseEntity<LoginUserResponseDto> loginUser(
		@RequestBody LoginUserRequestDto requestDto
	) {
		LoginUserResponseDto responseDto = userService.loginUser(requestDto);

		return ResponseEntity.status(HttpStatus.OK)
			.body(responseDto);
	}
}
