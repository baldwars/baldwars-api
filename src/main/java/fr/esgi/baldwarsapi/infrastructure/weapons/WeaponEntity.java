package fr.esgi.baldwarsapi.infrastructure.weapons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "weapon")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeaponEntity {

    @Id
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Integer level;

    @Column(nullable = false)
    private Integer damage;

    @Column(nullable = false)
    private Integer cost;

    @Column(name = "min_range", nullable = false)
    private Integer minRange;

    @Column(name = "max_range")
    private Integer maxRange;

    @Column(nullable = false)
    private Integer price;
}
