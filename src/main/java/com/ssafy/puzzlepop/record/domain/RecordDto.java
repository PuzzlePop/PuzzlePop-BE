package com.ssafy.puzzlepop.record.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RecordDto {

    private int id;
    private String userId;
    private int gameId;
}
