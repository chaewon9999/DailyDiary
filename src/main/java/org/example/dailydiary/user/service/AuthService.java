package org.example.dailydiary.user.service;

import org.example.dailydiary.user.dto.response.ReissueAccessTokenResponseDto;

public interface AuthService {

	ReissueAccessTokenResponseDto reissueAccessToken(String authorization);
}
