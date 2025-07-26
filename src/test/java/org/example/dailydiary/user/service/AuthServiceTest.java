package org.example.dailydiary.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.example.dailydiary.common.security.JwtUtil;
import org.example.dailydiary.common.security.RefreshTokenManager;
import org.example.dailydiary.user.dto.response.ReactiveUserResponseDto;
import org.example.dailydiary.user.dto.response.ReissueAccessTokenResponseDto;
import org.example.dailydiary.user.entity.User;
import org.example.dailydiary.user.entity.UserRole;
import org.example.dailydiary.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import io.jsonwebtoken.Claims;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

	@InjectMocks
	private AuthServiceImpl authService;

	@Mock
	private UserServiceImpl userService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private JwtUtil jwtUtil;

	@Mock
	private RefreshTokenManager tokenManager;

	@Mock
	private BCryptPasswordEncoder passwordEncoder;

	private User user;
	private User admin;
	private String refreshToken;
	private String bearerToken;

	@BeforeEach
	void setUp() {

		this.admin = User.builder()
			.email("admin@admin.com")
			.password(passwordEncoder.encode("password"))
			.nickname("nickname")
			.build();
		ReflectionTestUtils.setField(admin, "id", 1L);
		ReflectionTestUtils.setField(admin,"role", UserRole.ADMIN);

		this.user = User.builder()
			.email("test@test.com")
			.password(passwordEncoder.encode("password"))
			.nickname("nickname")
			.build();
		ReflectionTestUtils.setField(user, "id", 2L);
		ReflectionTestUtils.setField(user,"role", UserRole.USER);

		this.refreshToken = "authorization-refresh-token";
		this.bearerToken = "Bearer " + refreshToken;
	}

	@Test
	void Refresh_Token_재발급에_성공한다() {
		//given
		Claims claims = mock(Claims.class);

		when(jwtUtil.validToken(refreshToken)).thenReturn(claims);
		when(claims.getSubject()).thenReturn(String.valueOf(user.getId()));
		when(claims.get("role")).thenReturn(String.valueOf(user.getRole()));

		when(tokenManager.isValidRefreshToken(user.getId(), bearerToken)).thenReturn(true);

		String reissueToken = "reissue-access-token";
		when(jwtUtil.generateAccessToken(user.getId(), user.getRole())).thenReturn(reissueToken);

		//when
		ReissueAccessTokenResponseDto responseDto = authService.reissueAccessToken(bearerToken);

		//then
		assertThat(reissueToken).isEqualTo(responseDto.getAccessToken());
	}

	@Test
	void 삭제된_유저_복구에_성공한다() {
		//given
		Long adminId = admin.getId();
		Long userId = user.getId();

		user.deleteEntity();

		when(userService.findUserById(adminId)).thenReturn(admin);
		when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user));

		//when
		ReactiveUserResponseDto responseDto = authService.reativeUser(adminId, userId);

		//then
		assertThat("유저를 성공적으로 복구했습니다.").isEqualTo(responseDto.getMessage());
		assertThat(user.getDeletedAt()).isNull();
	}
}