package fr.esgi.baldwarsapi.domain.user;

import fr.esgi.baldwarsapi.domain.user.mappers.UserMapper;
import fr.esgi.baldwarsapi.domain.user.models.User;
import fr.esgi.baldwarsapi.domain.authentication.RegisterRequestBody;
import fr.esgi.baldwarsapi.domain.warrior.WarriorService;
import fr.esgi.baldwarsapi.domain.weapons.WeaponService;
import fr.esgi.baldwarsapi.exposition.weapons.PurchaseRequest;
import fr.esgi.baldwarsapi.infrastructure.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final WarriorService warriorService;
    private final WeaponService weaponService;
    private final UserMapper mapper;
    private final UserRepository repository;


    public List<User> findAll() {
        var users = new ArrayList<User>();
        var usersAsEntity = repository.findAll();

        if (!usersAsEntity.isEmpty()) {
            usersAsEntity.forEach(entity -> users.add(mapper.from(entity)));
        }

        return users;
    }

    public User findOneById(UUID id) {
        var optionalUser = repository.findById(id);

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        return mapper.from(optionalUser.get());
    }

    public User findOneByUsername(String username) {
        var users = findAll();

        if (users.isEmpty()) {
            throw new UserNotFoundException();
        }

        var optionalUser = users.stream().filter(user -> user.getUsername().equals(username)).findFirst();

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        return optionalUser.get();
    }

    public User findOneByWarrior(Integer warrior) {
        System.out.println("warrior winner: " + warrior);
        var optional = findAll().stream()
                .filter(user -> user.getWarrior().getId().equals(warrior))
                .findFirst();

        if (optional.isEmpty()) {
            throw new UserNotFoundException();
        }

        return optional.get();
    }

    public User addWarrior(User user, String name) {
        var warrior = this.warriorService.save(user, name);
        user.setWarrior(warrior);

        return mapper.from(this.repository.save(mapper.from(user)));
    }

    public User save(RegisterRequestBody body) {
        var userEntity = mapper.from(body);
        userEntity.setPassword(hashPassword(userEntity.getPassword()));

        var userEntityInserted =  repository.save(userEntity);
        var warrior = this.warriorService.save(mapper.from(userEntityInserted), body.getWarrior());
        userEntityInserted.setWarrior(warrior.getId());
        userEntityInserted = repository.save(userEntityInserted);

        var weapon = this.weaponService.findById(1);
        this.weaponService.purchaseWeapon(new PurchaseRequest(userEntityInserted.getId(), weapon));

        return mapper.from(userEntityInserted);
    }

    private String hashPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    public void gainExperience(UUID id) {
        var optionalUser = this.repository.findById(id);
      
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        }
      
        var updateUser = optionalUser.get();

        updateUser.setXp(updateUser.getXp() + Experience.GAIN * updateUser.getLevel());
        updateUser.setEloPoints(updateUser.getEloPoints() + Experience.ELIO_POINTS_WIN);

        if (updateUser.getXp() >= updateUser.getMaxXp()) {
            var xp = updateUser.getMaxXp() + (updateUser.getXp() - updateUser.getMaxXp());
            updateUser.setXp(xp);
            updateUser.setLevel(updateUser.getLevel() + 1);

            var maxXp = (int)(updateUser.getLevel() * Experience.LEVEL_UP_MULTIPLIER + updateUser.getMaxXp() * 1.25);
            updateUser.setMaxXp(maxXp);

            var warrior = this.warriorService
                    .increaseSkillPoints(updateUser.getLevel(), updateUser.getWarrior());
            updateUser.setWarrior(warrior.getId());
        }

        this.repository.save(updateUser);
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
