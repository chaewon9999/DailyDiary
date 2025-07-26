package org.example.dailydiary.user.controller;

import org.example.dailydiary.common.security.CustomUserPrincipal;
import org.example.dailydiary.user.dto.response.ReactiveUserResponseDto;
import org.example.dailydiary.user.dto.response.ReissueAccessTokenResponseDto;
import org.example.dailydiary.user.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Auth API")
@SecurityRequirement(name = "JWT")
public class AuthController {

	private final AuthService authService;

	/**
	 * 토큰 재발급
	 * @param request api 요청
	 * @return accessToken
	 */
	@PostMapping("/reissue")
	@Operation(summary = "토큰 재발급")
	public ResponseEntity<ReissueAccessTokenResponseDto> reissueAccessToken(
		HttpServletRequest request
	) {
		String authorization = request.getHeader("Authorization");

		ReissueAccessTokenResponseDto responseDto = authService.reissueAccessToken(authorization);

		return ResponseEntity.status(HttpStatus.CREATED)
			.body(responseDto);
	}

	/**
	 * 삭제된 유저 복구
	 * @param principal adminId, userRole
	 * @param userId 복구하려는 유저 id
	 * @return 복구 완료 메시지(String)
	 */
	@PatchMapping("/reactive/user/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "삭제된 유저 복구")
	public ResponseEntity<ReactiveUserResponseDto> reactiveUser(
		@AuthenticationPrincipal CustomUserPrincipal principal,
		@PathVariable Long userId
	) {
		ReactiveUserResponseDto responseDto = authService.reativeUser(principal.userIdConverter(), userId);

		return ResponseEntity.status(HttpStatus.OK)
			.body(responseDto);
	}

}
