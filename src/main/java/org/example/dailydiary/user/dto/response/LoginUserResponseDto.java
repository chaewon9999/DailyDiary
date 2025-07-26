package org.example.dailydiary.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginUserResponseDto {

	@Schema(description = "유저 아이디", example = "1")
	private final Long userId;

	@Schema(description = "엑세스 토큰")
	private final String accessToken;

	@Schema(description = "리프레쉬 토큰")
	private final String refreshToken;
}
