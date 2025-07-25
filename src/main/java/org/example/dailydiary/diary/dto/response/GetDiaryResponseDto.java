package org.example.dailydiary.diary.dto.response;

import java.time.LocalDateTime;

import org.example.dailydiary.diary.entity.Diary;
import org.example.dailydiary.diary.entity.Feeling;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class GetDiaryResponseDto {

	private final Long diaryId;

	private final String title;

	private final String contents;

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
