package fr.esgi.baldwarsapi.domain.warrior;

import fr.esgi.baldwarsapi.infrastructure.warrior.WarriorRepository;
import org.springframework.stereotype.Service;

@Service
public class WarriorService {
    private final WarriorRepository warriorRepository;

    public WarriorService(WarriorRepository warriorRepository) {
        this.warriorRepository = warriorRepository;
    }
}
