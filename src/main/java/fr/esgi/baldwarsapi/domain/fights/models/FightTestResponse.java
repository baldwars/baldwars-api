package fr.esgi.baldwarsapi.domain.fights.models;

import fr.esgi.baldwarsapi.domain.user.models.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FightTestResponse {

    private UserResponse winner;
    private FightOverview overview;

}
