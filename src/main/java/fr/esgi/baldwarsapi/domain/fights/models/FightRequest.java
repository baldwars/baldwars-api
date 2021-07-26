package fr.esgi.baldwarsapi.domain.fights.models;

import fr.esgi.baldwarsapi.domain.scripts.models.Script;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class FightRequest {

    private final Script script;
    private final UUID opponent;

}
