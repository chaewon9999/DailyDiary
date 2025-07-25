package org.example.dailydiary.user.controller;

import org.example.dailydiary.user.dto.response.ReissueAccessTokenResponseDto;
import org.example.dailydiary.user.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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
}
