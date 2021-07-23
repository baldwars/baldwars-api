package fr.esgi.baldwarsapi.domain.weapons.models;

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
    private Integer minRange;
    private Integer maxRange;

}
