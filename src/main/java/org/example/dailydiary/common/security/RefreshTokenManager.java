package org.example.dailydiary.common.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class RefreshTokenManager {

	private final Map<Long, String> userRefreshTokens = new ConcurrentHashMap<>();

	public void saveRefreshToken(Long userId, String refreshToken) {
		userRefreshTokens.put(userId, refreshToken);
	}

	public boolean isValidRefreshToken(Long userId, String refreshToken) {
		String storedToken = userRefreshTokens.get(userId);
		return refreshToken.equals(storedToken);
	}

	public void removeRefreshToken(Long userId) {
		userRefreshTokens.remove(userId); // 로그아웃시
	}

}
