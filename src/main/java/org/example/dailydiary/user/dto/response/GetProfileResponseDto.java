package org.example.dailydiary.user.dto.response;

import org.example.dailydiary.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class GetProfileResponseDto {

	private final Long userId;

	private final String email;

	private final String nickname;

	public GetProfileResponseDto(User user) {
		this.userId = user.getId();
		this.email = user.getEmail();
		this.nickname = user.getNickname();
	}
}
