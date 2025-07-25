package org.example.dailydiary.user.service;

import org.example.dailydiary.user.dto.request.CreateUserRequestDto;
import org.example.dailydiary.user.dto.request.LoginUserRequestDto;
import org.example.dailydiary.user.dto.response.CreateUserResponseDto;
import org.example.dailydiary.user.dto.response.LoginUserResponseDto;

public interface UserService {

	CreateUserResponseDto saveUser(CreateUserRequestDto requestDto);

	LoginUserResponseDto loginUser(LoginUserRequestDto requestDto);
}
