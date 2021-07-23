package fr.esgi.baldwarsapi.infrastructure.warrior;

import fr.esgi.baldwarsapi.domain.warrior.models.Warrior;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface WarriorRepository extends JpaRepository<WarriorEntity, Integer> {
    Optional<WarriorEntity> findWarriorEntityByOwner(UUID id);
}
