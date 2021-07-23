package fr.esgi.baldwarsapi.exposition.weapons;

import fr.esgi.baldwarsapi.domain.user.models.User;
import fr.esgi.baldwarsapi.domain.weapons.models.WeaponStore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequest {

    private UUID purchaser;
    private WeaponStore weapon;

}
