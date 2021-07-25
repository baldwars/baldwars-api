package fr.esgi.baldwarsapi.infrastructure.fights;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FightRepository extends JpaRepository<FightEntity, UUID> { }
