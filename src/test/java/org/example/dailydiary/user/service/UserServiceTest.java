package org.example.dailydiary.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.example.dailydiary.common.security.JwtUtil;
import org.example.dailydiary.common.security.RefreshTokenManager;
import org.example.dailydiary.user.dto.request.CreateUserRequestDto;
import org.example.dailydiary.user.dto.request.LoginUserRequestDto;
import org.example.dailydiary.user.dto.request.UpdateUserRequestDto;
import org.example.dailydiary.user.dto.response.CreateUserResponseDto;
import org.example.dailydiary.user.dto.response.GetProfileResponseDto;
import org.example.dailydiary.user.dto.response.LoginUserResponseDto;
import org.example.dailydiary.user.dto.response.LogoutUserResponseDto;
import org.example.dailydiary.user.dto.response.UpdateUserResponseDto;
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

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@InjectMocks
	private UserServiceImpl userService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private BCryptPasswordEncoder passwordEncoder;

	@Mock
	private JwtUtil jwtUtil;

	@Mock
	private RefreshTokenManager tokenManager;

	private User user;

	@BeforeEach
	void setUp() {

		this.user = User.builder()
			.email("test@test.com")
			.password(passwordEncoder.encode("password"))
			.nickname("nickname")
			.build();
		ReflectionTestUtils.setField(user, "id", 1L);
		ReflectionTestUtils.setField(user,"role", UserRole.USER);
	}

	@Test
	void 회원가입에_성공한다() {
		//given
		CreateUserRequestDto requestDto =
			new CreateUserRequestDto("test@test.com", "password", "password", "nickname");

		User createdUser = User.builder()
			.email(requestDto.getEmail())
			.password(passwordEncoder.encode(requestDto.getPassword()))
			.nickname(requestDto.getNickname())
			.build();
		ReflectionTestUtils.setField(createdUser, "id", 2L);

		when(userRepository.save(any(User.class))).thenReturn(createdUser);

		//when
		CreateUserResponseDto responseDto = userService.saveUser(requestDto);

		//then
		assertThat(createdUser.getId()).isEqualTo(responseDto.getUserId());
		assertThat("회원가입을 완료했습니다.").isEqualTo(responseDto.getMessage());
	}

	@Test
	void 로그인에_성공한다() {
		//given
		LoginUserRequestDto requestDto = new LoginUserRequestDto(user.getEmail(), "password");
		when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.ofNullable(user));
		when(passwordEncoder.matches("password", user.getPassword())).thenReturn(true);

		String accessToken = jwtUtil.generateAccessToken(user.getId(), user.getRole());
		String refreshToken = jwtUtil.generateRefreshToken(user.getId(), user.getRole());
		tokenManager.saveRefreshToken(user.getId(), refreshToken);

		//when
		LoginUserResponseDto responseDto = userService.loginUser(requestDto);

		//then
		assertThat(user.getId()).isEqualTo(responseDto.getUserId());
		assertThat(accessToken).isEqualTo(responseDto.getAccessToken());
		assertThat(refreshToken).isEqualTo(responseDto.getRefreshToken());
	}

	@Test
	void 로그아웃에_성공한다() {
		//given
		Long userId = user.getId();

		//when
		LogoutUserResponseDto responseDto = userService.logoutUser(userId);

		//then
		assertThat("로그아웃을 완료했습니다.").isEqualTo(responseDto.getMessage());
	}

	@Test
	void 유저_정보_조회에_성공한다() {
		//given
		Long userId = user.getId();
		when(userRepository.findByIdAndDeletedAtIsNull(userId)).thenReturn(Optional.ofNullable(user));

		//when
		GetProfileResponseDto responseDto = userService.getProfile(userId);

		//then
		assertThat(userId).isEqualTo(responseDto.getUserId());
		assertThat(user.getEmail()).isEqualTo(responseDto.getEmail());
		assertThat(user.getNickname()).isEqualTo(responseDto.getNickname());
	}

	@Test
	void 유저_정보_수정에_성공한다() {
		//given
		Long userId = user.getId();
		when(userRepository.findByIdAndDeletedAtIsNull(userId)).thenReturn(Optional.ofNullable(user));

		UpdateUserRequestDto requestDto =
			new UpdateUserRequestDto("fix@fix.com", "fixedpassword", "fixedpassword", "fix");

		//when
		UpdateUserResponseDto responseDto = userService.updateProfile(userId, requestDto);

		//then
		assertThat(userId).isEqualTo(responseDto.getUserId());
		assertThat("회원 정보 수정을 완료했습니다.").isEqualTo(responseDto.getMessage());
	}

	@Test
	void 유저_삭제에_성공한다() {
		//given
		Long userId = user.getId();
		when(userRepository.findByIdAndDeletedAtIsNull(userId)).thenReturn(Optional.ofNullable(user));

		//when
		userService.deleteUser(userId);

		//then
		assertThat(user.getDeletedAt()).isNotNull();
	}
}