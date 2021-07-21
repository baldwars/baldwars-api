package fr.esgi.baldwarsapi.domain.user.mappers;

import fr.esgi.baldwarsapi.domain.user.models.User;
import fr.esgi.baldwarsapi.infrastructure.user.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User from(UserEntity entity) {
        var user = new User();
        user.setId(entity.getId());
        user.setUsername(entity.getUsername());
        user.setFirstName(entity.getFirstName());
        user.setLastName(entity.getLastName());
        user.setEmail(entity.getEmail());
        user.setPassword(entity.getPassword());
        user.setXp(entity.getXp());
        user.setMaxXp(entity.getMaxXp());
        user.setLevel(entity.getLevel());
        user.setEloPoints(entity.getEloPoints());

        return user;
    }
}
