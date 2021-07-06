package fr.esgi.baldwarsapi.infrastructure.warrior;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "warriors")
@RequiredArgsConstructor
public class WarriorEntity {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private Integer level;

    @Column(nullable = false, unique = true)
    private Integer health;

    @Column(nullable = false, unique = true)
    private Integer moves;

    @Column(nullable = false, unique = true)
    private Integer actions;
}
