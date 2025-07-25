package org.example.dailydiary.user.service;

import org.example.dailydiary.common.exception.CustomException;
import org.example.dailydiary.common.exception.ErrorCode;
import org.example.dailydiary.common.security.JwtUtil;
import org.example.dailydiary.user.dto.request.CreateUserRequestDto;
import org.example.dailydiary.user.dto.request.LoginUserRequestDto;
import org.example.dailydiary.user.dto.request.UpdateUserRequestDto;
import org.example.dailydiary.user.dto.response.CreateUserResponseDto;
import org.example.dailydiary.user.dto.response.GetProfileResponseDto;
import org.example.dailydiary.user.dto.response.LoginUserResponseDto;
import org.example.dailydiary.user.dto.response.UpdateUserResponseDto;
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

	//회원가입
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

	//로그인
	@Transactional
	public LoginUserResponseDto loginUser(LoginUserRequestDto requestDto) {

		User user = findUserByEmail(requestDto.getEmail());

		if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
			throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
		}

		String accessToken = jwtUtil.generateAccessToken(user.getId(), user.getRole());
		String refreshToken = jwtUtil.generateRefreshToken(user.getId());

		return new LoginUserResponseDto(user.getId(), accessToken, refreshToken);
	}

	//유저 정보 조회
	@Transactional(readOnly = true)
	public GetProfileResponseDto getProfile(Long userId) {

		User user = findUserById(userId);

		return new GetProfileResponseDto(user);
	}

	//유저 정보 수정
	@Transactional
	public UpdateUserResponseDto updateProfile(Long userId, UpdateUserRequestDto requestDto) {

		User user = findUserById(userId);

		if (!requestDto.getPassword().equals(requestDto.getCheckPassword())) {
			throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
		}

		user.updateUser(requestDto, passwordEncoder.encode(requestDto.getPassword()));

		return new UpdateUserResponseDto(userId, "회원 정보 수정을 완료했습니다.");
	}

	//유저 삭제
	@Transactional
	public void deleteUser(Long userId) {

		User user = findUserById(userId);

		user.deleteEntity();

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
