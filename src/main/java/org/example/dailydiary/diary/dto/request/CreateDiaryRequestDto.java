package org.example.dailydiary.diary.dto.request;

import org.example.dailydiary.diary.entity.Feeling;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateDiaryRequestDto {

	@NotBlank(message = "제목을 입력해주세요.")
	@Size(max = 30, message = "제목은 최대 30자까지 입력 가능합니다.")
	@Schema(description = "제목", example = "title")
	private final String title;

	@NotBlank(message = "내용을 입력해주세요.")
	@Size(max = 1000, message = "내용은 최대 1000자까지 입력 가능합니다.")
	@Schema(description = "내용", example = "contents")
	private final String contents;

	@NotNull(message = "오늘의 기분을 입력해주세요.")
	@Schema(description = "감정", example = "HAPPY")
	private final Feeling feeling;
}
