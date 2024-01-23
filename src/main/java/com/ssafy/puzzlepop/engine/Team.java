package com.ssafy.puzzlepop.engine;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Team {
    private List<User> players;
}
