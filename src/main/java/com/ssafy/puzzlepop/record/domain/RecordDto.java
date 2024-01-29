package com.ssafy.puzzlepop.record.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ssafy.puzzlepop.team.domain.Team;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RecordDto {

    private int id;
    private String userId;
    private long gameId;

    @Builder
    public RecordDto(Record record) {
        this.id = record.getId();
        this.userId = record.getUserId();
        this.gameId = record.getGameId();
    }

    public Record toEntity() {
        return Record.builder()
                .id(this.id)
                .userId(this.userId)
                .gameId(this.gameId)
                .build();
    }
}
