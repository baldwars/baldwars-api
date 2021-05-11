package fr.esgi.baldwarsapi.domain.authentication;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LoginRequestResponse {
    private final String token;
}
