package org.example.dailydiary.user.repository;

import org.example.dailydiary.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
