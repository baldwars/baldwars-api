package fr.esgi.baldwarsapi.domain.warrior.mappers;

import fr.esgi.baldwarsapi.domain.warrior.models.Warrior;
import fr.esgi.baldwarsapi.infrastructure.warrior.WarriorEntity;
import org.springframework.stereotype.Component;

@Component
public class WarriorMapper {

    public Warrior from(WarriorEntity entity) {
        var warrior = new Warrior();
        warrior.setId(entity.getWarrior_id());
        warrior.setName(entity.getName());
        warrior.setLevel(entity.getLevel());
        warrior.setHealth(entity.getHealth());
        warrior.setMoves(entity.getMoves());
        warrior.setActions(entity.getActions());
        warrior.setOwner(entity.getOwner());
        warrior.setSkillPoints(entity.getSkillPoints());

        return warrior;
    }
}
