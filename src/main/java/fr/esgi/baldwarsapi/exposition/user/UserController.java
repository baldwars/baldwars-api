package fr.esgi.baldwarsapi.exposition.user;

import fr.esgi.baldwarsapi.domain.user.mappers.UserResponseMapper;
import fr.esgi.baldwarsapi.domain.user.UserService;
import fr.esgi.baldwarsapi.domain.user.UserNotFoundException;
import fr.esgi.baldwarsapi.domain.user.models.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserResponseMapper toUserResponse;
    private final UserService service;

    public UserController(UserResponseMapper toUserResponse, UserService service) {
        this.toUserResponse = toUserResponse;
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        var users = service.findAll();

        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        var response = new ArrayList<UserResponse>();
        users.forEach(user -> response.add(toUserResponse.from(user)));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findOneById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(toUserResponse.from(service.findOneById(id)));
        } catch (UserNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<UserResponse> findOneByUsername(@PathVariable String username) {
        try {
            return ResponseEntity.ok(toUserResponse.from(service.findOneByUsername(username)));
        } catch (UserNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
