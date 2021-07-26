package fr.esgi.baldwarsapi.exposition.fight;

import fr.esgi.baldwarsapi.domain.fights.CompilationErrorException;
import fr.esgi.baldwarsapi.domain.fights.ExecutionErrorException;
import fr.esgi.baldwarsapi.domain.fights.FightRequest;
import fr.esgi.baldwarsapi.domain.fights.FightService;
import fr.esgi.baldwarsapi.domain.scripts.models.Script;
import fr.esgi.baldwarsapi.domain.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fights")
@RequiredArgsConstructor
public class FightController {

    private final FightService service;

    @PostMapping("/test")
    public ResponseEntity<Object> testScript(@RequestBody Script script) {
        try {
            var response = this.service.test(script, "tester");
            return  ResponseEntity.ok(response);

        } catch (UserNotFoundException exception) {
            return ResponseEntity.badRequest().build();

        } catch (CompilationErrorException | ExecutionErrorException exception) {
            return ResponseEntity.ok(exception.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> fight(@RequestBody FightRequest request) {
        try {
            var response = this.service.fight(request.getScript(), request.getOpponent());
            return  ResponseEntity.ok(response);

        } catch (UserNotFoundException exception) {
            return ResponseEntity.badRequest().build();

        } catch (CompilationErrorException | ExecutionErrorException exception) {
            return ResponseEntity.ok(exception.getMessage());
        }
    }
}
