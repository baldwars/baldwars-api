package fr.esgi.baldwarsapi.infrastructure.warrior;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "warrior")
@NoArgsConstructor
@AllArgsConstructor
public class WarriorEntity {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer level;

    @Column(nullable = false)
    private Integer health;

    @Column(nullable = false)
    private Integer moves;

    @Column(nullable = false)
    private Integer actions;

    @Column(nullable = false, name = "skill_points")
    private Integer skillPoints;

}
