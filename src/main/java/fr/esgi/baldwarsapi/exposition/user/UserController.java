package fr.esgi.baldwarsapi.exposition.user;

import fr.esgi.baldwarsapi.domain.user.mappers.UserMapper;
import fr.esgi.baldwarsapi.domain.user.UserService;
import fr.esgi.baldwarsapi.domain.user.UserNotFoundException;
import fr.esgi.baldwarsapi.domain.user.models.UserResponse;
import fr.esgi.baldwarsapi.domain.warrior.WarriorAlreadyExistsException;
import fr.esgi.baldwarsapi.exposition.warrior.WarriorRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserMapper mapper;
    private final UserService service;

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        var users = service.findAll();

        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        var response = new ArrayList<UserResponse>();
        users.forEach(user -> response.add(mapper.to(user)));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findOneById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(mapper.to(service.findOneById(id)));

        } catch (UserNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<UserResponse> findOneByUsername(@PathVariable String username) {
        try {
            return ResponseEntity.ok(mapper.to(service.findOneByUsername(username)));

        } catch (UserNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/warrior")
    public ResponseEntity<?> addWarrior(@RequestBody WarriorRequest request) {
        try {
            var user = this.service.findOneById(request.getOwner());
            user = this.service.addWarrior(user, request.getName());

            return ResponseEntity.ok(user);

        } catch (WarriorAlreadyExistsException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        } catch (UserNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/experience")
    public ResponseEntity<UserResponse> gainExperience(@PathVariable UUID id) {
        try {
            var user = this.service.gainExperience(id);
            return ResponseEntity.ok(mapper.to(user));

        } catch (UserNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

}
