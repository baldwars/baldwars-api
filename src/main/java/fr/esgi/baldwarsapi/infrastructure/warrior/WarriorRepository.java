package fr.esgi.baldwarsapi.infrastructure.warrior;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarriorRepository extends JpaRepository<WarriorEntity, Integer> { }
