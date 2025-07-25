package org.example.dailydiary.diary.entity;

import org.example.dailydiary.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_diary")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Diary extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String contents;

	@Column(nullable = false)
	private Feeling feeling;

	@Builder
	public Diary(String title, String contents, Feeling feeling) {
		this.title = title;
		this.contents = contents;
		this.feeling = feeling;
	}
}
