package fr.esgi.baldwarsapi.infrastructure.user;

import fr.esgi.baldwarsapi.domain.warrior.models.Warrior;
import fr.esgi.baldwarsapi.infrastructure.warrior.WarriorEntity;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
@Table(name = "users")
@RequiredArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "elo_points", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer eloPoints;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registered;

    @Column(nullable = false)
    private Integer xp;

    @Column(nullable = false, name = "max_xp")
    private Integer maxXp;

    @Column(nullable = false)
    private Integer level;

    @Column(nullable = false, name = "skill_points")
    private Integer skillPoints;
}
