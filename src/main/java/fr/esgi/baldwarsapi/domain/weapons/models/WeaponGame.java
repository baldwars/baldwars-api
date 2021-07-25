package fr.esgi.baldwarsapi.domain.weapons.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeaponGame {

    private Integer id;
    private String name;
    private Integer level;
    private Integer damage;
    private Integer cost;

    @JsonProperty("min_range")
    private Integer minRange;

    @JsonProperty("max_range")
    private Integer maxRange;

}
