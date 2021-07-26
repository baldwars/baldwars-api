package fr.esgi.baldwarsapi.domain.fights;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.esgi.baldwarsapi.infrastructure.fights.FightEntity;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

@Component
public class FightMapper {

    public FightEntity from(String fight, UUID striker, UUID opponent) {
        return FightEntity.builder()
                .striker(striker)
                .defender(opponent)
                .overview(Base64.getEncoder().encodeToString(fight.getBytes()))
                .created(LocalDateTime.now())
                .build();
    }

    public FightEntity from(String fight, UUID striker, UUID opponent, UUID winner) {
        return FightEntity.builder()
                .striker(striker)
                .defender(opponent)
                .winner(winner)
                .overview(Base64.getEncoder().encodeToString(fight.getBytes()))
                .created(LocalDateTime.now())
                .build();
    }

    @SneakyThrows
    public FightOverview from(String json) {
        var mapper = new ObjectMapper();
        return mapper.readValue(json, FightOverview.class);
    }
}
