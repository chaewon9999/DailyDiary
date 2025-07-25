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
		String savedToken = userRefreshTokens.get(userId);
		return refreshToken.equals(savedToken);
	}

	public void removeRefreshToken(Long userId) {
		userRefreshTokens.remove(userId);
	}

	public String getRefreshToken(Long userId) {
		return userRefreshTokens.get(userId);
	}
}
