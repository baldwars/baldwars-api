package fr.esgi.baldwarsapi.domain.user.mappers;

import fr.esgi.baldwarsapi.domain.authentication.RegisterRequestBody;
import fr.esgi.baldwarsapi.infrastructure.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
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

        return entity;
    }
}
