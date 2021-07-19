package fr.esgi.baldwarsapi.infrastructure.scripts;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ScriptRepository extends JpaRepository<ScriptEntity, UUID> { }
