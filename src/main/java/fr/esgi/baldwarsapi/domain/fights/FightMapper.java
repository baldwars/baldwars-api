package fr.esgi.baldwarsapi.domain.fights;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.esgi.baldwarsapi.domain.fights.models.FightOverview;
import fr.esgi.baldwarsapi.domain.fights.models.FightResponse;
import fr.esgi.baldwarsapi.domain.user.UserService;
import fr.esgi.baldwarsapi.domain.user.mappers.UserMapper;
import fr.esgi.baldwarsapi.infrastructure.fights.FightEntity;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FightMapper {

    private final UserService service;
    private final UserMapper userMapper;

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

    public FightResponse from(FightEntity entity) {
        var striker = this.service.findOneById(entity.getStriker());
        var defender = this.service.findOneById(entity.getDefender());
        var bytes = Base64.getDecoder().decode(entity.getOverview());
        var overview = this.from(new String(bytes));

        return new FightResponse(
                entity.getId(),
                userMapper.to(striker),
                userMapper.to(defender),
                entity.getWinner(),
                overview);

    }
}
