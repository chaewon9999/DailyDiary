package org.example.dailydiary.user.service;

import org.example.dailydiary.common.exception.CustomException;
import org.example.dailydiary.common.exception.ErrorCode;
import org.example.dailydiary.user.dto.request.CreateUserRequestDto;
import org.example.dailydiary.user.entity.User;
import org.example.dailydiary.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

	private final UserRepository userRepository;

	@Override
	@Transactional
	public Long saveUser(CreateUserRequestDto requestDto) {

		if (isExistUser(requestDto.getEmail())) {
			throw new CustomException(ErrorCode.EXIST_USER);
		}

		if (!requestDto.getPassword().equals(requestDto.getCheckPassword())) {
			throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
		}

		User user = User.builder()
			.email(requestDto.getEmail())
			.password(requestDto.getPassword())
			.nickname(requestDto.getNickname())
			.build();

		User savedUser = userRepository.save(user);

		return savedUser.getId();
	}

	public boolean isExistUser(String email) {
		return userRepository.findByEmail(email) != null;
	}
}
