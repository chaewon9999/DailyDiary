package org.example.dailydiary.diary.repository;

import org.example.dailydiary.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

}
