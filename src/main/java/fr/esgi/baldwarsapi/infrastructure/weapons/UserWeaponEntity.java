package fr.esgi.baldwarsapi.infrastructure.weapons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "user_weapon")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWeaponEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "weapon_id")
    private Integer weaponId;

}
