package fr.esgi.baldwarsapi.exposition.warrior;

import fr.esgi.baldwarsapi.domain.user.UserService;
import fr.esgi.baldwarsapi.domain.warrior.exceptions.SkillPointAmountException;
import fr.esgi.baldwarsapi.domain.warrior.exceptions.WarriorNotFoundException;
import fr.esgi.baldwarsapi.domain.warrior.WarriorService;
import fr.esgi.baldwarsapi.domain.warrior.models.SkillRequest;
import fr.esgi.baldwarsapi.domain.warrior.models.Warrior;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warriors")
@RequiredArgsConstructor
public class WarriorController {

    private final WarriorService warriorService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<Warrior>> findAll() {
        List<Warrior> warriorList = this.warriorService.findAll();
        return new ResponseEntity<>(warriorList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Warrior> findOneById(@PathVariable Integer id) {
        Warrior warrior = this.warriorService.findWarriorById(id);
        return new ResponseEntity<>(warrior, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Warrior> updateWarriorSkill(
            @PathVariable("id") Integer id, @RequestBody SkillRequest request)
    {
        try {
            var warrior = this.warriorService.updateWarriorSkill(id, request);
            return ResponseEntity.ok(warrior);

        } catch (WarriorNotFoundException exception) {
            return  ResponseEntity.notFound().build();

        } catch (SkillPointAmountException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

}
