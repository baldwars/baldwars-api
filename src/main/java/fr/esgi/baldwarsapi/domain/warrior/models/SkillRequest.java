package fr.esgi.baldwarsapi.domain.warrior.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillRequest {

    private String skill;
    private Integer amount;

}
