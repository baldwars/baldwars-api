package fr.esgi.baldwarsapi.domain.warrior.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class Warrior {
    private Integer id;
    private String name;
    private Integer level;
    private Integer health;
    private Integer moves;
    private Integer actions;
}
