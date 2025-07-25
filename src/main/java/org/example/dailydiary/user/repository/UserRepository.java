package org.example.dailydiary.user.repository;

import java.util.Optional;

import org.example.dailydiary.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

	Optional<User> findByIdAndDeletedAtIsNull(Long id);

	Optional<User> findById(Long userId);

}
