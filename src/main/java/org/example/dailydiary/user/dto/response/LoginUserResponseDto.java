package org.example.dailydiary.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginUserResponseDto {

	private final Long userId;

	private final String accessToken;

	private final String refreshToken;
}
