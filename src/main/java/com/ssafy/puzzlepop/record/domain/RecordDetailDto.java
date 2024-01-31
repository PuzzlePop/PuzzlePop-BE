package com.ssafy.puzzlepop.record.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ssafy.puzzlepop.gameinfo.domain.GameInfoDto;
import com.ssafy.puzzlepop.team.domain.TeamDto;
import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RecordDetailDto {

    private GameInfoDto gameInfo;
    private List<TeamDto> teamList;
// TODO: TeamMember 구현 완료 시 주석 해제
//    private List<UserTeamDto> userTeamList1;
//    private List<UserTeamDto> userTeamList2;

}
