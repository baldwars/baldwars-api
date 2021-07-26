package fr.esgi.baldwarsapi.domain.fights.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FightOverview {

    private Integer winner;

    @JsonProperty("fight")
    private List<Object> rounds;
}
