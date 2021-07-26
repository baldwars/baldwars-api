package fr.esgi.baldwarsapi.domain.fights;

import fr.esgi.baldwarsapi.domain.user.models.UserResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class FightResponse {

    private final UUID id;
    private final UserResponse striker;
    private final UserResponse defender;
    private final UUID winner;
    private final FightOverview fight;

}
