package org.example.dailydiary.user.dto.response;

import org.example.dailydiary.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateUserResponseDto {

	private final Long userId;

	private final String message;
}
