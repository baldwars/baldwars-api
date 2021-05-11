package fr.esgi.baldwarsapi.domain.authentication;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
public class RegisterRequestBody {

    @NotNull
    @NotEmpty
    @NotBlank
    private final String username;

    @NotNull
    @NotEmpty
    @NotBlank
    private final String firstName;

    @NotNull
    @NotEmpty
    @NotBlank
    private final String lastName;

    @NotNull
    @NotEmpty
    @NotBlank
    private final String email;

    @NotNull
    @NotEmpty
    @NotBlank
    private final String password;
}
