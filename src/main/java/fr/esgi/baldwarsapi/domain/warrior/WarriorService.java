package fr.esgi.baldwarsapi.domain.warrior;

import fr.esgi.baldwarsapi.domain.user.UserService;
import fr.esgi.baldwarsapi.domain.warrior.mappers.WarriorEntityMapper;
import fr.esgi.baldwarsapi.domain.warrior.mappers.WarriorMapper;
import fr.esgi.baldwarsapi.domain.warrior.models.Warrior;
import fr.esgi.baldwarsapi.exposition.warrior.WarriorRequest;
import fr.esgi.baldwarsapi.infrastructure.warrior.WarriorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WarriorService {
    private final WarriorRepository warriorRepository;
    private final WarriorMapper warriorMapper;
    private final WarriorEntityMapper warriorEntityMapper;
    private final UserService userService;

    public WarriorService(WarriorRepository warriorRepository, WarriorMapper warriorMapper, WarriorEntityMapper warriorEntityMapper, UserService userService) {
        this.warriorRepository = warriorRepository;
        this.warriorMapper = warriorMapper;
        this.warriorEntityMapper = warriorEntityMapper;
        this.userService = userService;
    }

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
        //var user = this.userService.findOneById(warrior.getOwner());
        var owner = this.warriorRepository.findWarriorEntityByOwner(warrior.getOwner());
        if (owner.isPresent()) {
            throw new WarriorAlreadyExistsException();
        }

        var warriorEntity = this.warriorEntityMapper.from(warrior);

        var warriorEntityInserted = this.warriorRepository.save(warriorEntity);

        return this.warriorMapper.from(warriorEntityInserted);
    }
}
