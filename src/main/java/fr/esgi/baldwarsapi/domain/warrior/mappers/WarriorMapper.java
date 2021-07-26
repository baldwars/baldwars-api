package fr.esgi.baldwarsapi.domain.warrior.mappers;

import fr.esgi.baldwarsapi.domain.user.Experience;
import fr.esgi.baldwarsapi.domain.warrior.models.Warrior;
import fr.esgi.baldwarsapi.infrastructure.warrior.WarriorEntity;
import org.springframework.stereotype.Component;

@Component
public class WarriorMapper {

    public Warrior from(WarriorEntity entity) {
        return new Warrior(
                entity.getId(),
                entity.getName(),
                entity.getLevel(),
                entity.getHealth(),
                entity.getMoves(),
                entity.getActions(),
                entity.getSkillPoints()
        );
    }

    public WarriorEntity from(Warrior warrior) {
        return new WarriorEntity(
                warrior.getId(),
                warrior.getName(),
                warrior.getLevel(),
                warrior.getHealth(),
                warrior.getMoves(),
                warrior.getActions(),
                warrior.getSkillPoints()
        );
    }

    public Warrior from(String name) {
        return Warrior.builder()
                .name(name)
                .level(1)
                .health(100)
                .moves(3)
                .actions(10)
                .skillPoints(Experience.SKILL_POINTS)
                .build();
    }
}
