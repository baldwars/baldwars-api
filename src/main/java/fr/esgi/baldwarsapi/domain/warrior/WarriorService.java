package fr.esgi.baldwarsapi.domain.warrior;

import fr.esgi.baldwarsapi.domain.user.Experience;
import fr.esgi.baldwarsapi.domain.user.models.User;
import fr.esgi.baldwarsapi.domain.warrior.exceptions.SkillPointAmountException;
import fr.esgi.baldwarsapi.domain.warrior.exceptions.WarriorAlreadyExistsException;
import fr.esgi.baldwarsapi.domain.warrior.exceptions.WarriorNotFoundException;
import fr.esgi.baldwarsapi.domain.warrior.mappers.WarriorMapper;
import fr.esgi.baldwarsapi.domain.warrior.models.SkillRequest;
import fr.esgi.baldwarsapi.domain.warrior.models.Warrior;
import fr.esgi.baldwarsapi.infrastructure.warrior.WarriorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WarriorService {
    private final WarriorRepository repository;
    private final WarriorMapper mapper;

    public List<Warrior> findAll() {
        var warriors = new ArrayList<Warrior>();
        var warriorAsEntity =  this.repository.findAll();

        warriorAsEntity.forEach(entity -> warriors.add(mapper.from(entity)));

        return warriors;
    }

    public Warrior findWarriorById(Integer id) {
        var optionalWarrior =  this.repository.findById(id);
        if (optionalWarrior.isEmpty()) {
            throw new WarriorNotFoundException();
        }
        return mapper.from(optionalWarrior.get());
    }

    public Warrior save(User user, String name) {
        if (user.getWarrior() != null) {
            throw new WarriorAlreadyExistsException();
        }

        var warrior = this.mapper.from(name);
        var entity = this.mapper.from(warrior);
        var inserted = this.repository.save(entity);

        return this.mapper.from(inserted);
    }

    public Warrior increaseSkillPoints(Integer level, Integer warriorId) {
        var warrior = this.findWarriorById(warriorId);
        warrior.setLevel(level);
        warrior.setSkillPoints(warrior.getSkillPoints() + Experience.SKILL_POINTS);
        var entity = this.mapper.from(warrior);

        var modified = this.repository.save(entity);

        return this.mapper.from(modified);
    }

    public Warrior updateWarriorSkill(Integer id, SkillRequest request) {
        var warrior = this.findWarriorById(id);

        var updatedWarrior = this.updateSkill(warrior, request.getSkill(), request.getAmount());
        var entity = this.mapper.from(updatedWarrior);
        var modified = this.repository.save(entity);

        return this.mapper.from(modified);
    }

    private Warrior updateSkill(Warrior warrior, String skill, Integer amount) {
        switch (skill) {
            case "health": {
                if (amount * SkillCost.HEALTH_COST > warrior.getSkillPoints() || amount * SkillCost.HEALTH_COST < 1) {
                    throw new SkillPointAmountException();
                }

                var skillPoints = warrior.getSkillPoints() - amount * SkillCost.HEALTH_COST;
                warrior.setSkillPoints(skillPoints);
                var health = warrior.getHealth() + amount * SkillCost.HEALTH_GAIN;
                warrior.setHealth(health);
                break;
            }
            case "moves": {
                if (amount * SkillCost.MOVE_COST > warrior.getSkillPoints() || amount * SkillCost.MOVE_COST < 1) {
                    throw new SkillPointAmountException();
                }

                var skillPoints = warrior.getSkillPoints() - amount * SkillCost.MOVE_COST;
                warrior.setSkillPoints(skillPoints);
                var moves = warrior.getMoves() + amount * SkillCost.MOVE_GAIN;
                warrior.setMoves(moves);
                break;
            }
            case "actions": {
                if (amount * SkillCost.ACTION_COST > warrior.getSkillPoints() || amount * SkillCost.ACTION_COST < 1) {
                    throw new SkillPointAmountException();
                }

                var skillPoints = warrior.getSkillPoints() - amount * SkillCost.ACTION_COST;
                warrior.setSkillPoints(skillPoints);
                var moves = warrior.getMoves() + amount * SkillCost.ACTION_GAIN;
                warrior.setMoves(moves);
                break;
            }
        }

        return warrior;
    }
}
