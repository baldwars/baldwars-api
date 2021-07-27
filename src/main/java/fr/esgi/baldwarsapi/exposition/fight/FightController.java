package fr.esgi.baldwarsapi.exposition.fight;

import fr.esgi.baldwarsapi.domain.fights.exceptions.CompilationErrorException;
import fr.esgi.baldwarsapi.domain.fights.exceptions.ExecutionErrorException;
import fr.esgi.baldwarsapi.domain.fights.models.FightRequest;
import fr.esgi.baldwarsapi.domain.fights.FightService;
import fr.esgi.baldwarsapi.domain.fights.models.FightResponse;
import fr.esgi.baldwarsapi.domain.scripts.models.Script;
import fr.esgi.baldwarsapi.domain.user.UserNotFoundException;
import fr.esgi.baldwarsapi.domain.user.models.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/fights")
@RequiredArgsConstructor
public class FightController {

    private final FightService service;

    @PostMapping("/test")
    public ResponseEntity<?> testScript(@RequestBody Script script) {
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
    public ResponseEntity<?> fight(@RequestBody FightRequest request) {
        try {
            var response = this.service.fight(request.getScript(), request.getOpponent());
            return  ResponseEntity.ok(response);

        } catch (UserNotFoundException exception) {
            return ResponseEntity.badRequest().build();

        } catch (CompilationErrorException | ExecutionErrorException exception) {
            return ResponseEntity.ok(exception.getMessage());
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<FightResponse>> findUserFights(@PathVariable("id") UUID userId) {
        var fights = this.service.findUserFightsHistory(userId);
        return ResponseEntity.ok(fights);
    }
}
