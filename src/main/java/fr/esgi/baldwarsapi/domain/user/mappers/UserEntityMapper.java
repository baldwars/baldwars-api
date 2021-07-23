package fr.esgi.baldwarsapi.domain.user.mappers;

import fr.esgi.baldwarsapi.domain.authentication.RegisterRequestBody;
import fr.esgi.baldwarsapi.domain.user.UserExperience;
import fr.esgi.baldwarsapi.domain.user.models.User;
import fr.esgi.baldwarsapi.infrastructure.user.UserEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserEntityMapper {
    public UserEntity from(RegisterRequestBody body) {
        var entity = new UserEntity();
        entity.setUsername(body.getUsername());
        entity.setFirstName(body.getFirstName());
        entity.setLastName(body.getLastName());
        entity.setEmail(body.getEmail());
        entity.setPassword(body.getPassword());
        entity.setEloPoints(0);
        entity.setBaldCoins(10000);
        entity.setRegistered(LocalDateTime.now());
        entity.setMaxXp(UserExperience.MAX_XP);
        entity.setXp(UserExperience.DRAW_XP);
        entity.setLevel(1);
        entity.setSkillPoints(0);

        return entity;
    }

    public UserEntity from(User body) {
        var entity = new UserEntity();
        entity.setId(body.getId());
        entity.setUsername(body.getUsername());
        entity.setFirstName(body.getFirstName());
        entity.setLastName(body.getLastName());
        entity.setEmail(body.getEmail());
        entity.setPassword(body.getPassword());
        entity.setEloPoints(body.getEloPoints());
        entity.setRegistered(LocalDateTime.now());
        entity.setMaxXp(UserExperience.MAX_XP);
        entity.setXp(UserExperience.DRAW_XP);
        entity.setLevel(body.getLevel());
        entity.setSkillPoints(body.getSkillPoints());

        return entity;
    }
}
