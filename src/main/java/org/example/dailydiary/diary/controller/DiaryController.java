package org.example.dailydiary.diary.controller;

import org.example.dailydiary.common.security.CustomUserPrincipal;
import org.example.dailydiary.diary.dto.request.CreateDiaryRequestDto;
import org.example.dailydiary.diary.dto.request.UpdateDiaryRequestDto;
import org.example.dailydiary.diary.dto.response.CreateDiaryResponseDto;
import org.example.dailydiary.diary.dto.response.DeleteDiaryResponseDto;
import org.example.dailydiary.diary.dto.response.GetDiaryResponseDto;
import org.example.dailydiary.diary.dto.response.UpdateDiaryResponseDto;
import org.example.dailydiary.diary.service.DiaryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController {

	private final DiaryService diaryService;

	/**
	 * 일기 생성
	 * @param principal userId, userRole
	 * @param requestDto title, contents, feeling
	 * @return diaryId, 일기 생성 완료 메시지(String)
	 */
	@PostMapping
	public ResponseEntity<CreateDiaryResponseDto> createDiary(
		@AuthenticationPrincipal CustomUserPrincipal principal,
		@RequestBody @Valid CreateDiaryRequestDto requestDto
	) {
		CreateDiaryResponseDto response = diaryService.saveDiary(principal.userIdConverter(), requestDto);

		return ResponseEntity.status(HttpStatus.CREATED)
			.body(response);
	}

	/**
	 * 일기 전체 조회
	 * @param principal userId, userRole
	 * @param pageable 페이지 정보
	 * @return diaryId, title, contents, feeling, createdAt, modifiedAt
	 */
	@GetMapping
	public ResponseEntity<Page<GetDiaryResponseDto>> getAllDiary(
		@AuthenticationPrincipal CustomUserPrincipal principal,
		@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
	) {
		Page<GetDiaryResponseDto> diaries = diaryService.getAllDiary(principal.userIdConverter(), pageable);

		return ResponseEntity.status(HttpStatus.OK)
			.body(diaries);
	}

	/**
	 * 일기 단건 조회
	 * @param principal userId, userRole
	 * @param diaryId diaryId
	 * @return diaryId, title, contents, feeling, createdAt, modifiedAt
	 */
	@GetMapping("/{diaryId}")
	public ResponseEntity<GetDiaryResponseDto> getDiaryById(
		@AuthenticationPrincipal CustomUserPrincipal principal,
		@PathVariable Long diaryId
	) {
		GetDiaryResponseDto responseDto = diaryService.getDiaryById(principal.userIdConverter(), diaryId);

		return ResponseEntity.status(HttpStatus.OK)
			.body(responseDto);
	}

	/**
	 * 일기 수정
	 * @param principal userId, userRole
	 * @param diaryId diaryId
	 * @param requestDto title, contents, feeling
	 * @return diaryId, 일기 수정 완료 메시지(String)
	 */
	@PutMapping("/{diaryId}")
	public ResponseEntity<UpdateDiaryResponseDto> updateDiary(
		@AuthenticationPrincipal CustomUserPrincipal principal,
		@PathVariable Long diaryId,
		@RequestBody @Valid UpdateDiaryRequestDto requestDto
	) {
		UpdateDiaryResponseDto responseDto = diaryService.updateDiary(principal.userIdConverter(), diaryId, requestDto);

		return ResponseEntity.status(HttpStatus.OK)
			.body(responseDto);
	}

	/**
	 * 일기 삭제
	 * @param principal userId, userRole
	 * @param diaryId diaryId
	 * @return 일기 삭제 완료 메시지(String)
	 */
	@DeleteMapping("/{diaryId}")
	public ResponseEntity<DeleteDiaryResponseDto> deleteDiary(
		@AuthenticationPrincipal CustomUserPrincipal principal,
		@PathVariable Long diaryId
	) {
		DeleteDiaryResponseDto responseDto = diaryService.deleteDiary(principal.userIdConverter(), diaryId);

		return ResponseEntity.status(HttpStatus.OK)
			.body(responseDto);
	}
}
