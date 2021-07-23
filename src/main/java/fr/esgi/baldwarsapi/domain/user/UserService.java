package fr.esgi.baldwarsapi.domain.user;

import fr.esgi.baldwarsapi.domain.user.mappers.UserEntityMapper;
import fr.esgi.baldwarsapi.domain.user.mappers.UserMapper;
import fr.esgi.baldwarsapi.domain.user.models.User;
import fr.esgi.baldwarsapi.domain.authentication.RegisterRequestBody;
import fr.esgi.baldwarsapi.domain.user.models.UserResponse;
import fr.esgi.baldwarsapi.infrastructure.user.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserMapper toUser;
    private final UserEntityMapper toUserEntity;
    private final UserRepository repository;

    public UserService(UserMapper toUser, UserEntityMapper toUserEntity, UserRepository repository) {
        this.toUser = toUser;
        this.toUserEntity = toUserEntity;
        this.repository = repository;
    }

    public List<User> findAll() {
        var users = new ArrayList<User>();
        var usersAsEntity = repository.findAll();

        usersAsEntity.forEach(entity -> users.add(toUser.from(entity)));

        return users;
    }

    public User findOneById(UUID id) {
        var optionalUser = repository.findById(id);

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        return toUser.from(optionalUser.get());
    }

    public User findOneByUsername(String username) {
        var users = findAll().stream();
        var optionalUser = users.filter(user -> user.getUsername().equals(username)).findFirst();

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        return optionalUser.get();
    }

    public User save(RegisterRequestBody body) {
        var userEntity = toUserEntity.from(body);
        userEntity.setPassword(hashPassword(userEntity.getPassword()));
        var userEntityInserted =  repository.save(userEntity);
        return toUser.from(userEntityInserted);
    }

    private String hashPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    public void increaseXp(UUID id) {
        var optionalUser = this.repository.findById(id);
      
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        }
      
        var updateUser = optionalUser.get();
        updateUser.setXp(updateUser.getXp() + UserExperience.WIN_XP);
        
        if (updateUser.getXp().equals(UserExperience.MAX_XP)) {
            updateUser.setLevel(updateUser.getLevel() + 1);
            updateUser.setXp(0);
        }
      
        this.repository.save(updateUser);
    }

    public void increaseSkillPoints(UUID id) {
        var user = this.findOneById(id);
        user.setSkillPoints(user.getSkillPoints() + UserExperience.WIN_SP);
        var userEntity = this.toUserEntity.from(user);
      
        this.repository.save(userEntity);
    }

    public void updateBaldCoins(UUID id, Integer weaponPrice) {
        var optional = this.repository.findById(id);

        if (optional.isEmpty()) {
            throw new UserNotFoundException();
        }

        var entity = optional.get();
        entity.setBaldCoins(entity.getBaldCoins() - weaponPrice);

        this.repository.save(entity);
    }
}
