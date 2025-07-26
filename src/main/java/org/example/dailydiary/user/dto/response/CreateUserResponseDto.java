package org.example.dailydiary.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateUserResponseDto {

	@Schema(description = "유저 아이디", example = "1")
	private final Long userId;

	private final String message;

}
