package com.ssafy.puzzlepop.engine;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Team {
    private List<User> players;

    public void addPlayer(User user) {
        players.add(user);
    }

    public void deletePlayer(User user) {
        for (int i = players.size()-1; i >= 0; i--) {
            if (players.get(i).getId() == user.getId()) {
                players.remove(i);
                break;
            }
        }
    }
}
