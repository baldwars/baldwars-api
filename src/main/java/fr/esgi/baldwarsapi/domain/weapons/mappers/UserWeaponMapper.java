package fr.esgi.baldwarsapi.domain.weapons.mappers;

import fr.esgi.baldwarsapi.exposition.weapons.PurchaseRequest;
import fr.esgi.baldwarsapi.infrastructure.weapons.UserWeaponEntity;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class UserWeaponMapper {

    @SneakyThrows
    public UserWeaponEntity from(PurchaseRequest body) {
        var entity = new UserWeaponEntity();
        entity.setUserId(body.getPurchaser());
        entity.setWeaponId(body.getWeapon().getId());

        return entity;
    }

}
