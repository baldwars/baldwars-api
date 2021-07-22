package fr.esgi.baldwarsapi.domain.user.mappers;

import fr.esgi.baldwarsapi.domain.user.models.User;
import fr.esgi.baldwarsapi.domain.user.models.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserResponseMapper {

    public UserResponse from(User user) {
        var userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEloPoints(user.getEloPoints());
        userResponse.setXp(user.getXp());
        userResponse.setMaxXp(user.getMaxXp());
        userResponse.setLevel(user.getLevel());
        userResponse.setSkillPoints(user.getSkillPoints());

        return userResponse;
    }
}
