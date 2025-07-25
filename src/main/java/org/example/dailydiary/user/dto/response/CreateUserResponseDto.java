package org.example.dailydiary.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class CreateUserResponseDto {

	private final Long userId;

	private final String message;

	public CreateUserResponseDto(Long userId, String message) {
		this.userId = userId;
		this.message = message;
	}
}
