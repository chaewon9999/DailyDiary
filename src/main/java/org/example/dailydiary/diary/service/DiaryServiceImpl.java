package org.example.dailydiary.diary.service;

import org.example.dailydiary.common.exception.CustomException;
import org.example.dailydiary.common.exception.ErrorCode;
import org.example.dailydiary.diary.dto.request.CreateDiaryRequestDto;
import org.example.dailydiary.diary.dto.request.UpdateDiaryRequestDto;
import org.example.dailydiary.diary.dto.response.CreateDiaryResponseDto;
import org.example.dailydiary.diary.dto.response.GetDiaryResponseDto;
import org.example.dailydiary.diary.dto.response.UpdateDiaryResponseDto;
import org.example.dailydiary.diary.entity.Diary;
import org.example.dailydiary.diary.repository.DiaryRepository;
import org.example.dailydiary.user.entity.User;
import org.example.dailydiary.user.service.UserServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService{

	private final DiaryRepository diaryRepository;
	private final UserServiceImpl userService;

	@Override
	@Transactional
	public CreateDiaryResponseDto saveDiary(Long userId, CreateDiaryRequestDto requestDto) {

		User user = userService.findUserById(userId);

		Diary diary = Diary.builder()
			.title(requestDto.getTitle())
			.contents(requestDto.getContents())
			.feeling(requestDto.getFeeling())
			.user(user)
			.build();

		Diary savedDiary = diaryRepository.save(diary);

		return new CreateDiaryResponseDto(savedDiary.getId(), "일기를 저장했습니다");
	}

	@Override
	@Transactional(readOnly = true)
	public Page<GetDiaryResponseDto> getAllDiary(Long userId, Pageable pageable) {

		Page<Diary> diaries = diaryRepository.findByUserId(userId, pageable);

		return diaries.map(GetDiaryResponseDto::new);
	}

	@Override
	@Transactional(readOnly = true)
	public GetDiaryResponseDto getDiaryById(Long userId, Long diaryId) {

		Diary diary = findDiaryById(diaryId);
		isOwner(diary, userId);

		return new GetDiaryResponseDto(diary);
	}

	@Override
	@Transactional
	public UpdateDiaryResponseDto updateDiary(Long userId, Long diaryId, UpdateDiaryRequestDto requestDto) {

		Diary diary = findDiaryById(diaryId);
		isOwner(diary, userId);

		diary.updateDiary(requestDto);

		return new UpdateDiaryResponseDto(diaryId, "성공적으로 일기를 수정했습니다.");
	}

	public Diary findDiaryById(Long diaryId) {
		return diaryRepository.findById(diaryId)
			.orElseThrow(() -> new CustomException(ErrorCode.DIARY_NOT_FOUND));
	}

	public void isOwner(Diary diary, Long userId) {
		if (!diary.getUserId().equals(userId)) {
			throw new CustomException(ErrorCode.FORBIDDEN);
		}
	}
}
