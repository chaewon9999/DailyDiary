package org.example.dailydiary.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReissueAccessTokenResponseDto {

	@Schema(description = "엑세스 토큰")
	private final String accessToken;
}
