package fr.esgi.baldwarsapi.exposition.scripts;

import fr.esgi.baldwarsapi.domain.scripts.ScriptService;
import fr.esgi.baldwarsapi.domain.scripts.exceptions.NotUserScriptException;
import fr.esgi.baldwarsapi.domain.scripts.exceptions.ScriptAlreadyExistsException;
import fr.esgi.baldwarsapi.domain.scripts.exceptions.ScriptNotFoundException;
import fr.esgi.baldwarsapi.domain.scripts.models.Script;
import fr.esgi.baldwarsapi.domain.user.UserNotFoundException;
import fr.esgi.baldwarsapi.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/scripts")
@RequiredArgsConstructor
public class ScriptController {

    private final UserService userService;
    private final ScriptService service;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody ScriptRequest request) {
        try {
            this.userService.findOneById(request.getOwner());
            var id = this.service.save(request);

            return ResponseEntity.created(URI.create("/api/scripts/" + id)).build();

        } catch (UserNotFoundException exception) {
            return ResponseEntity.badRequest().build();

        } catch (ScriptAlreadyExistsException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Script script) {
        try {
            this.userService.findOneById(script.getOwner());
            this.service.update(script);

            return ResponseEntity.ok().build();

        } catch (UserNotFoundException | ScriptNotFoundException | NotUserScriptException exception) {
            return ResponseEntity.badRequest().build();

        } catch (ScriptAlreadyExistsException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Script>> findUserScripts(@PathVariable("id") UUID id) {
        try {
            this.userService.findOneById(id);
            var scripts = this.service.findAllUserScripts(id);

            return ResponseEntity.ok(scripts);

        } catch (UserNotFoundException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Script>> findAll() {
        var scripts = this.service.findAll();

        return ResponseEntity.ok(scripts);
    }
}
