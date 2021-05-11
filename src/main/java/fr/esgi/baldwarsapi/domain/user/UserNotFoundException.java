package fr.esgi.baldwarsapi.domain.user;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User not found.");
    }
}
