package fr.esgi.baldwarsapi.domain.user.models;

import fr.esgi.baldwarsapi.domain.warrior.models.Warrior;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private Integer eloPoints;
    private Integer xp;
    private Integer maxXp;
    private Integer level;
    private Integer baldCoins;
    private Warrior warrior;
}