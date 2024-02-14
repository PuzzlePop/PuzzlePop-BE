package com.ssafy.puzzlepop.engine.domain;

import lombok.*;

@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;
    private boolean isMember;
    private String sessionId;

    @Override
    public boolean equals(Object obj) {
        User u = (User)obj;
        if (this.id.equals(u.getId())) {
            return true;
        }

        return false;
    }
}
