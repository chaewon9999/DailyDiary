package org.example.dailydiary.user.controller;

import org.example.dailydiary.common.security.CustomUserPrincipal;
import org.example.dailydiary.user.dto.request.CreateUserRequestDto;
import org.example.dailydiary.user.dto.request.LoginUserRequestDto;
import org.example.dailydiary.user.dto.request.UpdateUserRequestDto;
import org.example.dailydiary.user.dto.response.CreateUserResponseDto;
import org.example.dailydiary.user.dto.response.DeleteUserResponseDto;
import org.example.dailydiary.user.dto.response.GetProfileResponseDto;
import org.example.dailydiary.user.dto.response.LoginUserResponseDto;
import org.example.dailydiary.user.dto.response.LogoutUserResponseDto;
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

	/**
	 * 회원가입
	 * @param requestDto email, password, checkPassword, nickname
	 * @return userId, 회원가입 완료 메시지(String)
	 */
	@PostMapping("/signup")
	public ResponseEntity<CreateUserResponseDto> createUser(
		@RequestBody @Valid CreateUserRequestDto requestDto
	) {
		CreateUserResponseDto responseDto = userService.saveUser(requestDto);

		return ResponseEntity.status(HttpStatus.CREATED)
			.body(responseDto);
	}

	/**
	 * 로그인
	 * @param requestDto email, password
	 * @return userId, accessToken, refreshToken
	 */
	@PostMapping("/login")
	public ResponseEntity<LoginUserResponseDto> loginUser(
		@RequestBody @Valid LoginUserRequestDto requestDto
	) {
		LoginUserResponseDto responseDto = userService.loginUser(requestDto);

		return ResponseEntity.status(HttpStatus.OK)
			.body(responseDto);
	}

	/**
	 * 로그아웃
	 * @param principal userId, userRole
	 * @return 로그아웃 완료 메시지(String)
	 */
	@DeleteMapping("/logout")
	public ResponseEntity<LogoutUserResponseDto> logoutUser(
		@AuthenticationPrincipal CustomUserPrincipal principal
	) {
		LogoutUserResponseDto responseDto = userService.logoutUser(principal.userIdConverter());

		return ResponseEntity.status(HttpStatus.OK)
			.body(responseDto);
	}

	/**
	 * 유저 정보 조회
	 * @param principal userId, userRole
	 * @return userId, email, nickname
	 */
	@GetMapping
	public ResponseEntity<GetProfileResponseDto> getProfile(
		@AuthenticationPrincipal CustomUserPrincipal principal
	) {
		GetProfileResponseDto responseDto = userService.getProfile(principal.userIdConverter());

		return ResponseEntity.status(HttpStatus.OK)
			.body(responseDto);
	}

	/**
	 * 유저 정보 수정
	 * @param principal userId, userRole
	 * @param requestDto email, password, checkPassword, nickname
	 * @return userId, 유저 정보 수정 완료 메시지(String)
	 */
	@PutMapping
	public ResponseEntity<UpdateUserResponseDto> updateProfile(
		@AuthenticationPrincipal CustomUserPrincipal principal,
		@RequestBody @Valid UpdateUserRequestDto requestDto
	) {
		UpdateUserResponseDto responseDto = userService.updateProfile(principal.userIdConverter(), requestDto);

		return ResponseEntity.status(HttpStatus.OK)
			.body(responseDto);
	}

	/**
	 * 유저 삭제
	 * @param principal userId, userRole
	 * @return 유저 삭제 완료 메시지(String)
	 */
	@DeleteMapping
	public ResponseEntity<DeleteUserResponseDto> deleteUser(
		@AuthenticationPrincipal CustomUserPrincipal principal
	) {
		userService.deleteUser(principal.userIdConverter());

		return ResponseEntity.status(HttpStatus.OK)
			.body(new DeleteUserResponseDto());
	}
}