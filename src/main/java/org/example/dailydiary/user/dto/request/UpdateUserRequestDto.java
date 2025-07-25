package org.example.dailydiary.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateUserRequestDto {

	@NotBlank(message = "이메일을 입력해주세요.")
	@Email(message = "올바른 이메일 형식이 아닙니다")
	private final String email;

	@NotBlank(message = "비밀번호를 입력해주세요.")
	@Size(min = 8, max = 20, message = "비밀번호는 8~20자 사이여야 합니다.")
	private final String password;

	@NotBlank(message = "확인용 비밀번호를 입력해주세요.")
	private final String checkPassword;

	@NotBlank(message = "닉네임을 입력해주세요.")
	@Size(min = 2, max = 6, message = "닉네임은 2~6자 사이여야 합니다.")
	private final String nickname;
}
