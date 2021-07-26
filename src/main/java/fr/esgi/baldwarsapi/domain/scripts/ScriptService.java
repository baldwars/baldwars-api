package fr.esgi.baldwarsapi.domain.scripts;

import fr.esgi.baldwarsapi.domain.scripts.exceptions.NotUserScriptException;
import fr.esgi.baldwarsapi.domain.scripts.exceptions.ScriptAlreadyExistsException;
import fr.esgi.baldwarsapi.domain.scripts.exceptions.ScriptNotFoundException;
import fr.esgi.baldwarsapi.domain.scripts.mappers.ScriptMapper;
import fr.esgi.baldwarsapi.domain.scripts.models.Script;
import fr.esgi.baldwarsapi.exposition.scripts.ScriptRequest;
import fr.esgi.baldwarsapi.infrastructure.scripts.ScriptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScriptService {

    private final ScriptMapper mapper;
    private final ScriptRepository repository;

    public List<Script> findAll() {
        var scripts = new ArrayList<Script>();
        var entities = this.repository.findAll();

        for (var entity : entities) {
            var script = mapper.from(entity);
            scripts.add(script);
        }

        return scripts;
    }

    public Script findOneById(UUID id) {
        var optionalEntity = this.repository.findById(id);

        if (optionalEntity.isEmpty()) {
            throw new ScriptNotFoundException();
        }

        var entity = optionalEntity.get();

        return mapper.from(entity);
    }

    public List<Script> findAllUserScripts(UUID userId) {

        var scripts = this.findAll();
        return scripts.stream()
                .filter(script -> script.getOwner().equals(userId))
                .collect(Collectors.toList());

    }

    public Script findUserScriptById(UUID userId, UUID id) {
        var scripts = this.findAllUserScripts(userId);

        var optional = scripts.stream()
                .filter(script -> script.getId().equals(id))
                .findFirst();

        if (optional.isEmpty()) {
            throw new ScriptNotFoundException();
        }

        return optional.get();
    }

    public Script findUserScriptByName(UUID userId, String name) {
        var scripts = this.findAllUserScripts(userId);

        var optional = scripts.stream()
                .filter(script -> script.getName().equals(name))
                .findFirst();

        if (optional.isEmpty()) {
            throw new ScriptNotFoundException();
        }

        return optional.get();
    }

    public UUID save(ScriptRequest request) {
        try {
            this.findUserScriptByName(request.getOwner(), request.getName());
            throw new ScriptAlreadyExistsException();

        } catch (ScriptNotFoundException exception) {
            var entity = mapper.from(request);
            var inserted = this.repository.save(entity);

            return inserted.getId();
        }
    }

    public void update(Script request) {
        var optional = this.repository.findById(request.getId());

        if (optional.isEmpty()) {
            throw new ScriptNotFoundException();
        }

        var entity = optional.get();
        if (!entity.getOwner().equals(request.getOwner())) {
            throw new NotUserScriptException();
        }

        if (!isScriptNameValid(request)) {
            throw new ScriptAlreadyExistsException();
        }

        var content = Base64.getEncoder().encodeToString(request.getContent().getBytes());
        entity.setContent(content);
        entity.setName(request.getName());
        entity.setLastUpdate(LocalDateTime.now());

        this.repository.save(entity);
    }

    public Script findUserDefenseScript(UUID userId) {
        var scripts = this.findAllUserScripts(userId);

        var optional = scripts.stream()
                .filter(Script::isDefense)
                .findFirst();

        if (optional.isEmpty()) {
            throw new ScriptNotFoundException();
        }

        return optional.get();
    }

    private boolean isScriptNameValid(Script request) {
        var script = this.findUserScriptByName(request.getOwner(), request.getName());
        return script.getId().equals(request.getId());
    }
}
