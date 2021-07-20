package fr.esgi.baldwarsapi.domain.user.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class User {
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Integer eloPoints;
    private Integer level;
    private Integer xp;
    private Integer maxXp;
}
