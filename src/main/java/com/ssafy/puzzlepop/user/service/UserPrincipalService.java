package com.ssafy.puzzlepop.user.service;

import com.ssafy.puzzlepop.user.domain.PrincipalDetails;
import com.ssafy.puzzlepop.user.domain.User;
import com.ssafy.puzzlepop.user.exception.UserNotFoundException;
import com.ssafy.puzzlepop.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserPrincipalService {

    private UserRepository userRepository;

    @Transactional
    public PrincipalDetails loadUserPrincipal(Authentication authentication) {

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        // provider
        String provider = "google";
        Long userId = principalDetails.getUser().getId();
        String email = principalDetails.getEmail();

        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new UserNotFoundException("토큰 유저 없음");
        });

        return principalDetails;
    }
}

