package fr.esgi.baldwarsapi.infrastructure.warrior;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WarriorRepository extends JpaRepository<WarriorEntity, Integer> {
}
