package fr.esgi.baldwarsapi.domain.user.mappers;

import fr.esgi.baldwarsapi.domain.authentication.RegisterRequestBody;
import fr.esgi.baldwarsapi.domain.user.Experience;
import fr.esgi.baldwarsapi.domain.user.models.User;
import fr.esgi.baldwarsapi.domain.user.models.UserResponse;
import fr.esgi.baldwarsapi.domain.warrior.WarriorNotFoundException;
import fr.esgi.baldwarsapi.domain.warrior.WarriorService;
import fr.esgi.baldwarsapi.domain.warrior.models.Warrior;
import fr.esgi.baldwarsapi.infrastructure.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final WarriorService service;

    public User from(UserEntity entity) {
        Warrior warrior = null;
        try {
            warrior = this.service.findWarriorById(entity.getWarrior());

        } catch (WarriorNotFoundException ignored) { }
        return new User(
                entity.getId(),
                entity.getUsername(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getEloPoints(),
                entity.getLevel(),
                entity.getXp(),
                entity.getMaxXp(),
                entity.getBaldCoins(),
                warrior
        );
    }

    public UserEntity from(RegisterRequestBody body) {
        return new UserEntity(
                UUID.randomUUID(),
                body.getUsername(),
                body.getFirstName(),
                body.getLastName(),
                0,
                body.getEmail(),
                body.getPassword(),
                10000,
                LocalDateTime.now(),
                Experience.START,
                Experience.LEVEL_UP_MULTIPLIER,
                1,
                0
        );
    }

    public UserEntity from(User user) {
        var entity = new UserEntity();

        entity.setId(user.getId());
        entity.setUsername(user.getUsername());
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        entity.setEloPoints(user.getEloPoints());
        entity.setRegistered(LocalDateTime.now());
        entity.setMaxXp(user.getMaxXp());
        entity.setXp(Experience.START);
        entity.setLevel(user.getLevel());
        entity.setWarrior(user.getWarrior().getId());
        entity.setBaldCoins(user.getBaldCoins());

        return entity;
    }

    public UserResponse to(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEloPoints(),
                user.getXp(),
                user.getMaxXp(),
                user.getLevel(),
                user.getBaldCoins(),
                user.getWarrior()
        );
    }
}
