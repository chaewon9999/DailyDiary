package org.example.dailydiary.user.service;

import org.example.dailydiary.common.exception.CustomException;
import org.example.dailydiary.common.exception.ErrorCode;
import org.example.dailydiary.common.security.JwtUtil;
import org.example.dailydiary.common.security.RefreshTokenManager;
import org.example.dailydiary.user.dto.response.ReactiveUserResponseDto;
import org.example.dailydiary.user.dto.response.ReissueAccessTokenResponseDto;
import org.example.dailydiary.user.entity.User;
import org.example.dailydiary.user.entity.UserRole;
import org.example.dailydiary.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;
	private final RefreshTokenManager tokenManager;
	private final UserServiceImpl userService;

	//토큰 재발급
	@Override
	@Transactional
	public ReissueAccessTokenResponseDto reissueAccessToken(String authorization) {

		if (authorization == null || !authorization.startsWith("Bearer ")) {
			throw new CustomException(ErrorCode.INVALID_TOKEN);
		}

		String refreshToken = authorization.substring(7);
		Claims claims = jwtUtil.validToken(refreshToken);

		if (claims == null) {
			throw new CustomException(ErrorCode.INVALID_TOKEN);
		}

		Long userId = Long.parseLong(claims.getSubject());

		if (!tokenManager.isValidRefreshToken(userId, authorization)) {
			System.out.println("saved: " + tokenManager.getRefreshToken(userId));
			System.out.println("new: " + authorization);
			throw new CustomException(ErrorCode.INVALID_TOKEN);
		}

		UserRole role = UserRole.valueOf((String)claims.get("role"));
		String accessToken = jwtUtil.generateAccessToken(userId, role);

		return new ReissueAccessTokenResponseDto(accessToken);
	}

	//삭제된 유저 복구
	@Override
	@Transactional
	public ReactiveUserResponseDto reativeUser(Long adminId, Long userId) {

		User admin = userService.findUserById(adminId);

		if (admin.getRole() != UserRole.ADMIN) {
			throw new CustomException(ErrorCode.FORBIDDEN);
		}

		User user = userRepository.findById(userId)
			.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

		user.reactiveUser();

		return new ReactiveUserResponseDto();
	}
}
