package com.ssafy.puzzlepop.teamuser.repository;

import com.ssafy.puzzlepop.team.domain.Team;
import com.ssafy.puzzlepop.teamuser.domain.TeamUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamUserRepository extends JpaRepository<TeamUser, Long> {
    List<TeamUser> findAllByTeamId(Long teamId);
    List<TeamUser> findAllByUserId(Long userId);
}
