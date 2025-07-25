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

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/reissue")
	public ResponseEntity<ReissueAccessTokenResponseDto> reissueAccessToken(
		HttpServletRequest request
	) {
		String authorization = request.getHeader("Authorization");

		ReissueAccessTokenResponseDto responseDto = authService.reissueAccessToken(authorization);

		return ResponseEntity.status(HttpStatus.CREATED)
			.body(responseDto);
	}

	@PatchMapping("/reactive/user/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ReactiveUserResponseDto> reactiveUser(
		@AuthenticationPrincipal CustomUserPrincipal principal,
		@PathVariable Long userId
	) {
		ReactiveUserResponseDto responseDto = authService.reativeUser(principal.userIdConverter(), userId);

		return ResponseEntity.status(HttpStatus.OK)
			.body(responseDto);
	}

}
