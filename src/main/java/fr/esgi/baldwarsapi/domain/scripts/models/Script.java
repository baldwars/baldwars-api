package fr.esgi.baldwarsapi.domain.scripts.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Script {

    private UUID id;
    private UUID owner;
    private String name;
    private String content;
    @JsonProperty("isDefense")
    private boolean isDefense;

}
