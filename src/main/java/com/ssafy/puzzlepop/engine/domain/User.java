package com.ssafy.puzzlepop.engine.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;
    private boolean isMember;

    @Override
    public boolean equals(Object obj) {
        User u = (User)obj;
        if (this.id.equals(u.getId())) {
            return true;
        }

        return false;
    }
}
