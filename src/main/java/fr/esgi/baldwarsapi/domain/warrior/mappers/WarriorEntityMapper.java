package fr.esgi.baldwarsapi.domain.warrior.mappers;

import fr.esgi.baldwarsapi.domain.warrior.models.Warrior;
import fr.esgi.baldwarsapi.exposition.warrior.WarriorRequest;
import fr.esgi.baldwarsapi.infrastructure.warrior.WarriorEntity;
import org.springframework.stereotype.Component;


@Component
public class WarriorEntityMapper {

    public WarriorEntity from(Warrior body) {
        var entity = new WarriorEntity();
        entity.setName(body.getName());
        entity.setLevel(body.getLevel());
        entity.setHealth(body.getHealth());
        entity.setMoves(body.getMoves());
        entity.setActions(body.getActions());
        entity.setSkillPoints(body.getSkillPoints());
        entity.setOwner(body.getOwner());

        return entity;
    }

    public WarriorEntity from(WarriorRequest body) {
        var entity = new WarriorEntity();
        entity.setName(body.getName());
        entity.setLevel(1);
        entity.setHealth(100);
        entity.setMoves(3);
        entity.setActions(10);
        entity.setOwner(body.getOwner());
        entity.setSkillPoints(0);

        return entity;
    }
}
