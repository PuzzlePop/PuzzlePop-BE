package com.ssafy.puzzlepop.record.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ssafy.puzzlepop.gameinfo.domain.GameInfoDto;
import com.ssafy.puzzlepop.team.domain.TeamDto;
import com.ssafy.puzzlepop.teamuser.domain.TeamUserDto;
import com.ssafy.puzzlepop.teamuser.domain.TeamUserResponseDto;
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
    private List<TeamUserResponseDto> userTeamList1;
    private List<TeamUserResponseDto> userTeamList2;

}
