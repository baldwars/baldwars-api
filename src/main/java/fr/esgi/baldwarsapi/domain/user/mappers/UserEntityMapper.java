package fr.esgi.baldwarsapi.domain.user.mappers;

import fr.esgi.baldwarsapi.domain.authentication.RegisterRequestBody;
import fr.esgi.baldwarsapi.domain.user.UserExperience;
import fr.esgi.baldwarsapi.infrastructure.user.UserEntity;
import lombok.RequiredArgsConstructor;
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
        entity.setRank(0);
        entity.setRegistered(LocalDateTime.now());
        entity.setMaxXp(UserExperience.MAX_XP);
        entity.setXp(UserExperience.DRAW_XP);
        entity.setLevel(1);

        return entity;
    }
}
