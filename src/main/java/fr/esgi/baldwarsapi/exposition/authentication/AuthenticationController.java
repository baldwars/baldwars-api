package fr.esgi.baldwarsapi.exposition.authentication;


import fr.esgi.baldwarsapi.domain.authentication.LoginRequestBody;
import fr.esgi.baldwarsapi.configuration.JwtTokenUtil;
import fr.esgi.baldwarsapi.domain.authentication.LoginRequestResponse;
import fr.esgi.baldwarsapi.domain.user.UserService;
import fr.esgi.baldwarsapi.domain.user.UserNotFoundException;
import fr.esgi.baldwarsapi.domain.authentication.RegisterRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final RegisterRequestBodyValidator validator;

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
    public ResponseEntity<LoginRequestResponse> login(@RequestBody LoginRequestBody request) throws Exception {
        final var  username = request.getUsername();
        final var password = request.getPassword();

        authenticate(username, password);
        final var userDetails = userDetailsService.loadUserByUsername(username);
        final var token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new LoginRequestResponse(token));
    }

    private void authenticate(@NonNull String username, @NonNull String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}