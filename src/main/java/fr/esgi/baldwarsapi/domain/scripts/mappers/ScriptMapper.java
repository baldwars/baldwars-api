package fr.esgi.baldwarsapi.domain.scripts.mappers;

import fr.esgi.baldwarsapi.domain.scripts.models.ScriptRequest;
import fr.esgi.baldwarsapi.infrastructure.scripts.ScriptEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Base64;

@Component
public class ScriptMapper {

    public ScriptEntity from(ScriptRequest request) {
        var entity = new ScriptEntity();
        var now = LocalDateTime.now();

        entity.setOwner(request.getOwner());
        entity.setName(request.getName());
        entity.setContent(Base64.getEncoder().encodeToString(request.getContent().getBytes()));
        entity.setCreatedAt(now);
        entity.setLastUpdate(now);

        return entity;
    }

}
