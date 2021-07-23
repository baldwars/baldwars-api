package fr.esgi.baldwarsapi.exposition.warrior;

import fr.esgi.baldwarsapi.domain.warrior.WarriorService;
import fr.esgi.baldwarsapi.domain.warrior.models.Warrior;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/warriors")
public class WarriorController {

    private final WarriorService warriorService;

    public WarriorController(WarriorService warriorService) {
        this.warriorService = warriorService;
    }

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

    @PostMapping
    public ResponseEntity<Warrior> addOne(@RequestBody WarriorRequest warrior) {
        System.out.println(warrior);
        var savedWarrior = this.warriorService.save(warrior);
        return new ResponseEntity<>(savedWarrior, HttpStatus.OK);
    }
}
