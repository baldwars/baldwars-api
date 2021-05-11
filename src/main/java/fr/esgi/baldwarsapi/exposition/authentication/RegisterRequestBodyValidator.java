package fr.esgi.baldwarsapi.exposition.authentication;

import fr.esgi.baldwarsapi.domain.authentication.RegisterRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterRequestBodyValidator {

    public Boolean validate(RegisterRequestBody user) {
        return isUsernameValid(user.getUsername())
                && isFirstNameValid(user.getFirstName())
                && isLastNameValid(user.getLastName())
                && isEmailValid(user.getEmail())
                && isPasswordValid(user.getPassword());
    }

    private boolean isUsernameValid(String username) {
        return username.length() >= RegistrationPropertiesLength.USERNAME_MIN_LENGTH.value
                && username.length() <= RegistrationPropertiesLength.USERNAME_MAX_LENGTH.value;
    }

    private boolean isFirstNameValid(String firstName) {
        return firstName.length() >= RegistrationPropertiesLength.FIRSTNAME_MIN_LENGTH.value
                && firstName.length() <= RegistrationPropertiesLength.FIRSTNAME_MAX_LENGTH.value;
    }

    private boolean isLastNameValid(String lastName) {
        return lastName.length() >= RegistrationPropertiesLength.LASTNAME_MIN_LENGTH.value
                && lastName.length() <= RegistrationPropertiesLength.LASTNAME_MAX_LENGTH.value;
    }

    private boolean isEmailValid(String email) {
        var splitEmail = email.split("@");
        return email.length() >= RegistrationPropertiesLength.EMAIL_MIN_LENGTH.value
                && email.length() <= RegistrationPropertiesLength.EMAIL_MAX_LENGTH.value
                && splitEmail.length == 2
                && splitEmail[0].length() >= RegistrationPropertiesLength.EMAIL_NAME_MIN_LENGTH.value
                && splitEmail[0].length() <= RegistrationPropertiesLength.EMAIL_NAME_MAX_LENGTH.value
                && splitEmail[1].length() >= RegistrationPropertiesLength.EMAIL_DOMAIN_MIN_LENGTH.value
                && splitEmail[1].length() <= RegistrationPropertiesLength.EMAIL_DOMAIN_MAX_LENGTH.value;
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= RegistrationPropertiesLength.PASSWORD_MIN_LENGTH.value
                && password.length() <= RegistrationPropertiesLength.PASSWORD_MAX_LENGTH.value;
    }
}
