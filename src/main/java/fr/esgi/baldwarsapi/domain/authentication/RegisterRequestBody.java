package fr.esgi.baldwarsapi.domain.authentication;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RegisterRequestBody {

    private final String username;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
}
