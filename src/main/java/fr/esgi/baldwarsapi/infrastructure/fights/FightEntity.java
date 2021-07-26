package fr.esgi.baldwarsapi.infrastructure.fights;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "fight")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FightEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID striker;

    @Column(nullable = false)
    private UUID defender;

    private UUID winner;

    @Lob
    @Column(nullable = false)
    private String overview;

    @Column(updatable = false, nullable = false)
    private LocalDateTime created;
}
