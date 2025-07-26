package org.example.dailydiary.diary.dto.response;

import java.time.LocalDateTime;

import org.example.dailydiary.diary.entity.Diary;
import org.example.dailydiary.diary.entity.Feeling;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class GetDiaryResponseDto {

	@Schema(description = "다이어리 아이디", example = "1")
	private final Long diaryId;

	@Schema(description = "제목", example = "title")
	private final String title;

	@Schema(description = "내용", example = "contents")
	private final String contents;

	@Schema(description = "감정", example = "HAPPY")
	private final Feeling feeling;

	private final LocalDateTime createdAt;

	private final LocalDateTime modifiedAt;

	public GetDiaryResponseDto(Diary diary) {
		this.diaryId = diary.getId();
		this.title = diary.getTitle();
		this.contents = diary.getContents();
		this.feeling = diary.getFeeling();
		this.createdAt = diary.getCreatedAt();
		this.modifiedAt = diary.getModifiedAt();
	}
}
