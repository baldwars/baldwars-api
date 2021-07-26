package fr.esgi.baldwarsapi.domain.fights.models;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class Fight {

    private UUID id;
    private UUID striker;
    private UUID defender;
    private String overview;
    private LocalDateTime created;

}
