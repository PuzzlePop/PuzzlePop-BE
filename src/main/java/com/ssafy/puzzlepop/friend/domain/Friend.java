package com.ssafy.puzzlepop.friend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "from_user_id")
    private Long fromUserId;

    @NotNull
    @Column(name = "to_user_id")
    private Long toUserId;

    @NotNull
    @Column(name = "request_status", length = 32)
    private String requestStatus;

    public void update(FriendDto friendDto) {
        this.id = friendDto.getId();
        this.fromUserId = friendDto.getFromUserId();
        this.toUserId = friendDto.getToUserId();
        this.requestStatus = friendDto.getRequestStatus();
    }
}
