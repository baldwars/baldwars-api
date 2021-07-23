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
        user.setEloPoints(entity.getEloPoints());
        user.setBaldCoins(entity.getBaldCoins());

        return user;
    }
}
