package org.example.dailydiary.user.dto.response;

import org.example.dailydiary.user.entity.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class GetProfileResponseDto {

	@Schema(description = "유저 아이디", example = "1")
	private final Long userId;

	@Schema(description = "이메일", example = "test@test.com")
	private final String email;

	@Schema(description = "닉네임", example = "nickname")
	private final String nickname;

	public GetProfileResponseDto(User user) {
		this.userId = user.getId();
		this.email = user.getEmail();
		this.nickname = user.getNickname();
	}
}
