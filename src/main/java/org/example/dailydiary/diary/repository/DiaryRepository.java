package org.example.dailydiary.diary.repository;

import org.example.dailydiary.diary.entity.Diary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

	@EntityGraph(attributePaths = {"user"})
	Page<Diary> findByUserId(Long userId, Pageable pageable);
}
