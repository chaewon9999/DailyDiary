package org.example.dailydiary.user.service;

import org.example.dailydiary.user.dto.request.CreateUserRequestDto;

public interface UserService {

	public Long saveUser(CreateUserRequestDto requestDto);
}
