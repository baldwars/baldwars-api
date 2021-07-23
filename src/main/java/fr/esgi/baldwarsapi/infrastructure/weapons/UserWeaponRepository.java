package fr.esgi.baldwarsapi.infrastructure.weapons;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserWeaponRepository extends JpaRepository<UserWeaponEntity, UUID> { }
