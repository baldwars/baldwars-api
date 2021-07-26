package fr.esgi.baldwarsapi.domain.scripts.mappers;

import fr.esgi.baldwarsapi.domain.scripts.models.Script;
import fr.esgi.baldwarsapi.exposition.scripts.ScriptRequest;
import fr.esgi.baldwarsapi.infrastructure.scripts.ScriptEntity;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
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
        entity.setDefense(request.getIsDefense());
        entity.setCreatedAt(now);
        entity.setLastUpdate(now);

        return entity;
    }

    public Script from(ScriptEntity entity) {
        var script = new Script(entity.getId(), entity.getOwner(), entity.getName(),
                entity.getContent(), entity.isDefense());

        var bytes = Base64.getDecoder().decode(script.getContent());
        var content = new String(bytes, StandardCharsets.UTF_8);

        script.setContent(content);

        return script;
    }

}
