package fr.esgi.baldwarsapi.domain.warrior;

import fr.esgi.baldwarsapi.domain.experience.Experience;
import fr.esgi.baldwarsapi.domain.user.UserService;
import fr.esgi.baldwarsapi.domain.warrior.mappers.WarriorEntityMapper;
import fr.esgi.baldwarsapi.domain.warrior.mappers.WarriorMapper;
import fr.esgi.baldwarsapi.domain.warrior.models.Warrior;
import fr.esgi.baldwarsapi.exposition.warrior.WarriorRequest;
import fr.esgi.baldwarsapi.infrastructure.warrior.WarriorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WarriorService {
    private final WarriorRepository warriorRepository;
    private final WarriorMapper warriorMapper;
    private final WarriorEntityMapper warriorEntityMapper;

    public List<Warrior> findAll() {
        var warriors = new ArrayList<Warrior>();
        var warriorAsEntity =  this.warriorRepository.findAll();

        warriorAsEntity.forEach(entity -> warriors.add(warriorMapper.from(entity)));

        return warriors;
    }

    public Warrior findWarriorById(Integer id) {
        var optionalWarrior =  this.warriorRepository.findById(id);
        if (optionalWarrior.isEmpty()) {
            throw new WarriorNotFoundException();
        }
        return warriorMapper.from(optionalWarrior.get());
    }

    public Warrior save(WarriorRequest warrior) {
        var owner = this.warriorRepository.findWarriorEntityByOwner(warrior.getOwner());
        if (owner.isPresent()) {
            throw new WarriorAlreadyExistsException();
        }

        var warriorEntity = this.warriorEntityMapper.from(warrior);

        var warriorEntityInserted = this.warriorRepository.save(warriorEntity);

        return this.warriorMapper.from(warriorEntityInserted);
    }

    public Warrior increaseSkillPoints(Integer id) {
        var warrior = this.findWarriorById(id);
        warrior.setSkillPoints(warrior.getSkillPoints() + Experience.WIN_SKILL_POINTS);
        var entity = this.warriorEntityMapper.from(warrior);

        var modified = this.warriorRepository.save(entity);

        return this.warriorMapper.from(modified);
    }
}
