package org.example.dailydiary.user.service;

import org.example.dailydiary.common.exception.CustomException;
import org.example.dailydiary.common.exception.ErrorCode;
import org.example.dailydiary.common.security.JwtUtil;
import org.example.dailydiary.user.dto.request.CreateUserRequestDto;
import org.example.dailydiary.user.dto.request.LoginUserRequestDto;
import org.example.dailydiary.user.dto.response.CreateUserResponseDto;
import org.example.dailydiary.user.dto.response.GetProfileResponseDto;
import org.example.dailydiary.user.dto.response.LoginUserResponseDto;
import org.example.dailydiary.user.entity.User;
import org.example.dailydiary.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;
	private final BCryptPasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public CreateUserResponseDto saveUser(CreateUserRequestDto requestDto) {

		if (isExistUser(requestDto.getEmail())) {
			throw new CustomException(ErrorCode.EXIST_USER);
		}

		if (!requestDto.getPassword().equals(requestDto.getCheckPassword())) {
			throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
		}

		User user = User.builder()
			.email(requestDto.getEmail())
			.password(passwordEncoder.encode(requestDto.getPassword()))
			.nickname(requestDto.getNickname())
			.build();

		User savedUser = userRepository.save(user);

		return new CreateUserResponseDto(savedUser.getId(), "회원가입을 완료했습니다.");
	}

	public LoginUserResponseDto loginUser(LoginUserRequestDto requestDto) {

		User user = findUserByEmail(requestDto.getEmail());

		if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
			throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
		}

		String accessToken = jwtUtil.generateAccessToken(user.getId(), user.getRole());
		String refreshToken = jwtUtil.generateRefreshToken(user.getId());

		return new LoginUserResponseDto(user.getId(), accessToken, refreshToken);
	}

	public GetProfileResponseDto getProfile(Long userId) {

		User user = findUserById(userId);

		return new GetProfileResponseDto(user);
	}

	public boolean isExistUser(String email) {
		return userRepository.findByEmail(email).isPresent();
	}

	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email)
			.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
	}

	public User findUserById(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
	}
}
