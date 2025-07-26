package org.example.dailydiary.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginUserRequestDto {

	@NotBlank(message = "이메일을 입력해주세요.")
	@Schema(description = "이메일", example = "test@test.com")
	private final String email;

	@NotBlank(message = "비밀번호를 입력해주세요.")
	@Schema(description = "비밀번호", example = "password")
	private final String password;
}
