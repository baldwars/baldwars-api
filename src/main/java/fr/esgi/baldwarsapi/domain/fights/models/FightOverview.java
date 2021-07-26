package fr.esgi.baldwarsapi.domain.fights.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class FightOverview {

    private final Integer winner;

    @JsonProperty("fight")
    private final List<Object> rounds;
}
