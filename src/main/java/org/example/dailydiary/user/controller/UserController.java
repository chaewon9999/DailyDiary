package org.example.dailydiary.user.controller;

import org.example.dailydiary.common.security.CustomUserPrincipal;
import org.example.dailydiary.user.dto.request.CreateUserRequestDto;
import org.example.dailydiary.user.dto.request.LoginUserRequestDto;
import org.example.dailydiary.user.dto.request.UpdateUserRequestDto;
import org.example.dailydiary.user.dto.response.CreateUserResponseDto;
import org.example.dailydiary.user.dto.response.DeleteUserResponseDto;
import org.example.dailydiary.user.dto.response.GetProfileResponseDto;
import org.example.dailydiary.user.dto.response.LoginUserResponseDto;
import org.example.dailydiary.user.dto.response.UpdateUserResponseDto;
import org.example.dailydiary.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<CreateUserResponseDto> createUser(
		@RequestBody @Valid CreateUserRequestDto requestDto
	) {
		CreateUserResponseDto responseDto = userService.saveUser(requestDto);

		return ResponseEntity.status(HttpStatus.CREATED)
			.body(responseDto);
	}

	@PostMapping("/login")
	public ResponseEntity<LoginUserResponseDto> loginUser(
		@RequestBody @Valid LoginUserRequestDto requestDto
	) {
		LoginUserResponseDto responseDto = userService.loginUser(requestDto);

		return ResponseEntity.status(HttpStatus.OK)
			.body(responseDto);
	}

	@GetMapping
	public ResponseEntity<GetProfileResponseDto> getProfile(
		@AuthenticationPrincipal CustomUserPrincipal principal
	) {
		GetProfileResponseDto responseDto = userService.getProfile(principal.userIdConverter());

		return ResponseEntity.status(HttpStatus.OK)
			.body(responseDto);
	}

	@PutMapping
	public ResponseEntity<UpdateUserResponseDto> updateProfile(
		@AuthenticationPrincipal CustomUserPrincipal principal,
		@RequestBody @Valid UpdateUserRequestDto requestDto
	) {
		UpdateUserResponseDto responseDto = userService.updateProfile(principal.userIdConverter(), requestDto);

		return ResponseEntity.status(HttpStatus.OK)
			.body(responseDto);
	}

	@DeleteMapping
	public ResponseEntity<DeleteUserResponseDto> deleteUser(
		@AuthenticationPrincipal CustomUserPrincipal principal
	) {
		userService.deleteUser(principal.userIdConverter());

		return ResponseEntity.status(HttpStatus.OK)
			.body(new DeleteUserResponseDto());
	}
}