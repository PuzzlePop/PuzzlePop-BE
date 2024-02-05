package com.ssafy.puzzlepop.friend.domain;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FriendDto {

    private Long id;
    private Long fromUserId;
    private Long toUserId;
    private String requestStatus;

    @Builder
    public FriendDto(Friend friend) {
        this.id = friend.getId();
        this.fromUserId = friend.getFromUserId();
        this.toUserId = friend.getToUserId();
        this.requestStatus = friend.getRequestStatus();
    }
    public Friend toEtity() {
        return Friend.builder()
                .id(this.id)
                .fromUserId(this.fromUserId)
                .toUserId(this.toUserId)
                .requestStatus(this.requestStatus)
                .build();
    }
}
