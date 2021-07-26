package fr.esgi.baldwarsapi.domain.weapons;

import fr.esgi.baldwarsapi.domain.user.UserService;
import fr.esgi.baldwarsapi.domain.weapons.exceptions.NotEnoughBaldCoinsException;
import fr.esgi.baldwarsapi.domain.weapons.exceptions.UserAlreadyOwnsWeaponException;
import fr.esgi.baldwarsapi.domain.weapons.exceptions.WeaponNotFoundException;
import fr.esgi.baldwarsapi.domain.weapons.mappers.UserWeaponMapper;
import fr.esgi.baldwarsapi.domain.weapons.mappers.WeaponMapper;
import fr.esgi.baldwarsapi.domain.weapons.models.WeaponGame;
import fr.esgi.baldwarsapi.domain.weapons.models.WeaponStore;
import fr.esgi.baldwarsapi.exposition.weapons.PurchaseRequest;
import fr.esgi.baldwarsapi.infrastructure.weapons.UserWeaponEntity;
import fr.esgi.baldwarsapi.infrastructure.weapons.UserWeaponRepository;
import fr.esgi.baldwarsapi.infrastructure.weapons.WeaponEntity;
import fr.esgi.baldwarsapi.infrastructure.weapons.WeaponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeaponService {

    private final UserWeaponMapper userWeaponMapper;
    private final WeaponMapper weaponMapper;
    private final WeaponRepository weaponRepository;
    private final UserWeaponRepository userWeaponRepository;

    public List<WeaponStore> findStoreWeapons() {
        return this.weaponRepository.findAll(Sort.by("level"))
                .stream()
                .map(weaponMapper::from)
                .collect(Collectors.toList());
    }

    public List<WeaponStore> findUserWeapons(UUID userId) {
        var weaponIds = this.userWeaponRepository.findAll(Sort.by("weaponId"))
                .stream()
                .filter(e -> e.getUserId().equals(userId))
                .map(UserWeaponEntity::getWeaponId)
                .collect(Collectors.toList());

        return this.weaponRepository.findAllById(weaponIds)
                .stream()
                .map(weaponMapper::from)
                .collect(Collectors.toList());

    }

    public WeaponStore findById(Integer id) {
        var optional = this.weaponRepository.findById(id);

        if (optional.isEmpty()) {
            throw new WeaponNotFoundException();
        }

        return weaponMapper.from(optional.get());
    }

    public List<WeaponGame> findUserWeaponsToFight(UUID userId) {
        var weaponIds = this.userWeaponRepository.findAll(Sort.by("weaponId"))
                .stream()
                .filter(e -> e.getUserId().equals(userId))
                .map(UserWeaponEntity::getWeaponId)
                .collect(Collectors.toList());

        return this.weaponRepository.findAllById(weaponIds)
                .stream()
                .map(weaponMapper::from)
                .map(weaponMapper::from)
                .collect(Collectors.toList());

    }

    public void purchaseWeapon(PurchaseRequest request) {
        var weaponIds = this.findUserWeapons(request.getPurchaser())
                .stream()
                .map(WeaponStore::getId)
                .collect(Collectors.toList());

        if (weaponIds.contains(request.getWeapon().getId())) {
            throw new UserAlreadyOwnsWeaponException();
        }

        var weapon = this.weaponRepository.findById(request.getWeapon().getId());

        if (weapon.isEmpty()) {
            throw new WeaponNotFoundException();
        }

        this.userWeaponRepository.save(userWeaponMapper.from(request));
    }

    public void init() {
        var pistol = initWeapon(1,"Pistol", 1, 12, 3, 1, 5, 0);
        var shotgun = initWeapon(2,"Shotgun", 10, 31, 5, 1, 3, 5000);
        var katana = initWeapon(3, "Katana", 40, 73, 5, 1, 1, 12000);
        var blaster = initWeapon(4,"Blaster", 80, 100, 7, 3, 7, 50000);

        var entities = new ArrayList<WeaponEntity>();
        entities.add(pistol);
        entities.add(shotgun);
        entities.add(katana);
        entities.add(blaster);

        this.weaponRepository.saveAll(entities);
    }

    private WeaponEntity initWeapon(Integer id, String name, Integer level, Integer damage,
                                    Integer cost, Integer minRange, Integer maxRange, Integer price)
    {
        return WeaponEntity.builder()
                .id(id)
                .name(name)
                .level(level)
                .damage(damage)
                .cost(cost)
                .minRange(minRange)
                .maxRange(maxRange)
                .price(price)
                .build();
    }

}
