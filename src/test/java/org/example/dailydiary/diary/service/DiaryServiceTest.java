package org.example.dailydiary.diary.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.example.dailydiary.common.exception.CustomException;
import org.example.dailydiary.common.exception.ErrorCode;
import org.example.dailydiary.diary.dto.request.CreateDiaryRequestDto;
import org.example.dailydiary.diary.dto.request.UpdateDiaryRequestDto;
import org.example.dailydiary.diary.dto.response.CreateDiaryResponseDto;
import org.example.dailydiary.diary.dto.response.DeleteDiaryResponseDto;
import org.example.dailydiary.diary.dto.response.GetDiaryResponseDto;
import org.example.dailydiary.diary.dto.response.UpdateDiaryResponseDto;
import org.example.dailydiary.diary.entity.Diary;
import org.example.dailydiary.diary.entity.Feeling;
import org.example.dailydiary.diary.repository.DiaryRepository;
import org.example.dailydiary.user.entity.User;
import org.example.dailydiary.user.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class DiaryServiceTest {

	@InjectMocks
	private DiaryServiceImpl diaryService;

	@Mock
	private DiaryRepository diaryRepository;

	@Mock
	private UserServiceImpl userService;

	private User user;
	private Diary diary1;
	private Diary diary2;

	@BeforeEach
	void setUp() {
		this.user = User.builder()
			.email("test@test.com")
			.password("password")
			.nickname("nickname")
			.build();
		ReflectionTestUtils.setField(user, "id", 1L);

		this.diary1 = Diary.builder()
			.title("title1")
			.contents("contents1")
			.feeling(Feeling.HAPPY)
			.user(user)
			.build();
		ReflectionTestUtils.setField(diary1, "id", 1L);

		this.diary2 = Diary.builder()
			.title("title2")
			.contents("contents2")
			.feeling(Feeling.SAD)
			.user(user)
			.build();
		ReflectionTestUtils.setField(diary2, "id", 1L);
	}

	@Test
	void 일기_생성에_성공한다() {
		//given
		when(userService.findUserById(1L)).thenReturn(user);

		CreateDiaryRequestDto requestDto = new CreateDiaryRequestDto("title", "contents", Feeling.HAPPY);

		Diary createdDiary = Diary.builder()
			.title(requestDto.getTitle())
			.contents(requestDto.getContents())
			.feeling(requestDto.getFeeling())
			.user(user)
			.build();
		ReflectionTestUtils.setField(createdDiary, "id", 3L);

		when(diaryRepository.save(any(Diary.class))).thenReturn(createdDiary);

		//when
		CreateDiaryResponseDto responseDto = diaryService.saveDiary(user.getId(), requestDto);

		//then
		assertThat(3L).isEqualTo(responseDto.getDiaryId());
		assertThat("일기를 저장했습니다.").isEqualTo(responseDto.getMessage());
	}

	@Test
	void 일기_전체_조회에_성공한다() {
		//given
		Pageable pageable = PageRequest.of(0, 10);

		List<Diary> diaries = List.of(diary1, diary2);
		PageImpl<Diary> page = new PageImpl<>(diaries, pageable, diaries.size());

		when(diaryRepository.findByUser_Id(user.getId(), pageable)).thenReturn(page);

		//when
		Page<GetDiaryResponseDto> responseDto = diaryService.getAllDiary(user.getId(), pageable);

		//then
		assertThat(2).isEqualTo(responseDto.getContent().size());
		assertThat(diary1.getContents()).isEqualTo(responseDto.getContent().get(0).getContents());
		assertThat(diary2.getContents()).isEqualTo(responseDto.getContent().get(1).getContents());
	}

	@Test
	void 일기_단건_조회에_성공한다() {
		//given
		when(diaryRepository.findById(diary1.getId())).thenReturn(Optional.ofNullable(diary1));

		//when
		GetDiaryResponseDto responseDto = diaryService.getDiaryById(user.getId(), diary1.getId());

		//then
		assertThat(diary1.getId()).isEqualTo(responseDto.getDiaryId());
		assertThat(diary1.getTitle()).isEqualTo(responseDto.getTitle());
		assertThat(diary1.getContents()).isEqualTo(responseDto.getContents());
		assertThat(diary1.getFeeling()).isEqualTo(responseDto.getFeeling());
	}

	@Test
	void 일기_수정에_성공한다() {
		//given
		when(diaryRepository.findById(diary1.getId())).thenReturn(Optional.ofNullable(diary1));
		UpdateDiaryRequestDto requestDto = new UpdateDiaryRequestDto("fix", "fix", diary1.getFeeling());

		diary1.updateDiary(requestDto);

		//when
		UpdateDiaryResponseDto responseDto = diaryService.updateDiary(user.getId(), diary1.getId(), requestDto);

		//then
		assertThat(diary1.getId()).isEqualTo(responseDto.getDiaryId());
		assertThat("일기 수정을 완료했습니다.").isEqualTo(responseDto.getMessage());
	}

	@Test
	void 일기_삭제에_성공한다() {
		//given
		when(diaryRepository.findById(diary1.getId())).thenReturn(Optional.ofNullable(diary1));

		//when
		DeleteDiaryResponseDto responseDto = diaryService.deleteDiary(diary1.getUserId(), diary1.getId());

		//then
		assertThat("성공적으로 삭제되었습니다.").isEqualTo(responseDto.getMessage());
	}

	@Test
	void 일기_아이디가_일치하지_않아_삭제_실패한다() {
		//given
		Long invalidDiaryId = 5L;
		Long diaryId = diary1.getId();

		when(diaryRepository.findById(invalidDiaryId)).thenReturn(Optional.empty());

		//when
		CustomException e = assertThrows(CustomException.class,
			() -> diaryService.deleteDiary(user.getId(), invalidDiaryId));

		//then
		assertThat(ErrorCode.DIARY_NOT_FOUND).isEqualTo(e.getErrorCode());
	}
}