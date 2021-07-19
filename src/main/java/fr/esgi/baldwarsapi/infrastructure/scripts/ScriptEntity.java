package fr.esgi.baldwarsapi.infrastructure.scripts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "script")
@NoArgsConstructor
@AllArgsConstructor
public class ScriptEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID owner;

    @Column(nullable = false)
    private String content;

    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;
}
