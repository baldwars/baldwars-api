package fr.esgi.baldwarsapi.exposition.authentication;

import fr.esgi.baldwarsapi.configuration.TokenProvider;
import fr.esgi.baldwarsapi.domain.authentication.LoginRequestBody;
import fr.esgi.baldwarsapi.domain.authentication.RegisterRequestBody;
import fr.esgi.baldwarsapi.domain.user.UserNotFoundException;
import fr.esgi.baldwarsapi.domain.user.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManager;
    private final UserService userService;
    private final RegisterRequestBodyValidator validator;

    public AuthenticationController(
            TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManager,
            UserService userService, RegisterRequestBodyValidator validator)
    {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.validator = validator;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestBody body) {
        var isValid = validator.validate(body);
        if (!isValid) {
            return ResponseEntity.badRequest().build();
        }

        try {
            userService.findOneByUsername(body.getUsername());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        } catch (UserNotFoundException exception) {
            var user = userService.save(body);
            return ResponseEntity.created(URI.create("/api/users/" + user.getId().toString())).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestBody body) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(body.getUsername(), body.getPassword());

        var authentication = authenticationManager.getObject().authenticate(authenticationToken);

        var token = tokenProvider.createToken(authentication);
        var httpHeaders = new HttpHeaders();
        httpHeaders.add(AUTHORIZATION, "Bearer " + token);

        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
    }
}