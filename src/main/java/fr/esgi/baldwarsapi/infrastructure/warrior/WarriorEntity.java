package fr.esgi.baldwarsapi.infrastructure.warrior;

import fr.esgi.baldwarsapi.infrastructure.user.UserEntity;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Table(name = "warriors")
@RequiredArgsConstructor
public class WarriorEntity {
    @Id
    @GeneratedValue
    private Integer warrior_id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Integer level;

    @Column(nullable = false)
    private Integer health;

    @Column(nullable = false)
    private Integer moves;

    @Column(nullable = false)
    private Integer actions;


    @Column(nullable = false, unique = true)
    private UUID owner;

}
