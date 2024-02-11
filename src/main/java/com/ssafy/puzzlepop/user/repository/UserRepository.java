package com.ssafy.puzzlepop.user.repository;

import com.ssafy.puzzlepop.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByIdAndEmail(Long id, String email);
    Optional<User> findByEmail(String email);
    List<User> findAllByEmailContaining(String email);
    List<User> findAllByNicknameContaining(String nickname);
    boolean existsByRefreshToken(String refreshToken);
}
