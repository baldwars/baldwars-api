package fr.esgi.baldwarsapi.domain.weapons.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.esgi.baldwarsapi.domain.weapons.models.WeaponGame;
import fr.esgi.baldwarsapi.domain.weapons.models.WeaponStore;
import fr.esgi.baldwarsapi.infrastructure.weapons.WeaponEntity;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class WeaponMapper {

    @SneakyThrows
    public WeaponStore from(WeaponEntity entity) {
        var mapper = new ObjectMapper();
        var json = mapper.writeValueAsString(entity);

        return mapper.readValue(json, WeaponStore.class);
    }

    public WeaponGame from(WeaponStore weaponStore) {
        return new WeaponGame(
                weaponStore.getId(),
                weaponStore.getName(),
                weaponStore.getLevel(),
                weaponStore.getDamage(),
                weaponStore.getCost(),
                weaponStore.getMinRange(),
                weaponStore.getMaxRange()
        );
    }

}
