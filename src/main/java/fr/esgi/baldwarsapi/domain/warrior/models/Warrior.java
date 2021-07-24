package fr.esgi.baldwarsapi.domain.warrior.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Warrior {
    private Integer id;
    private String name;
    private Integer level;
    private Integer health;
    private Integer moves;
    private Integer actions;
    private Integer skillPoints;
}
