package fr.esgi.baldwarsapi.domain.warrior;

import fr.esgi.baldwarsapi.domain.user.Experience;
import fr.esgi.baldwarsapi.domain.user.models.User;
import fr.esgi.baldwarsapi.domain.warrior.mappers.WarriorMapper;
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

    public Warrior increaseSkillPoints(Integer id) {
        var warrior = this.findWarriorById(id);
        warrior.setSkillPoints(warrior.getSkillPoints() + Experience.SKILL_POINTS);
        var entity = this.mapper.from(warrior);

        var modified = this.repository.save(entity);

        return this.mapper.from(modified);
    }
}
