package com.ssafy.puzzlepop.user.repository;

import com.ssafy.puzzlepop.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
