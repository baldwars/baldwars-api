package fr.esgi.baldwarsapi.infrastructure.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "elo_points", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer eloPoints;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "bald_coins", columnDefinition = "integer default 10000")
    private Integer baldCoins;

    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registered;

    @Column(nullable = false)
    private Integer xp;

    @Column(nullable = false, name = "max_xp")
    private Integer maxXp;

    @Column(nullable = false)
    private Integer level;

    @Column(name = "warrior_id")
    private Integer warrior;

}
