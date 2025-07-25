package org.example.dailydiary.user.service;

import org.example.dailydiary.user.dto.request.CreateUserRequestDto;
import org.example.dailydiary.user.dto.request.LoginUserRequestDto;
import org.example.dailydiary.user.dto.request.UpdateUserRequestDto;
import org.example.dailydiary.user.dto.response.CreateUserResponseDto;
import org.example.dailydiary.user.dto.response.GetProfileResponseDto;
import org.example.dailydiary.user.dto.response.LoginUserResponseDto;
import org.example.dailydiary.user.dto.response.UpdateUserResponseDto;

public interface UserService {

	CreateUserResponseDto saveUser(CreateUserRequestDto requestDto);

	LoginUserResponseDto loginUser(LoginUserRequestDto requestDto);

	GetProfileResponseDto getProfile(Long userId);

	UpdateUserResponseDto updateProfile(Long userId, UpdateUserRequestDto requestDto);
}
