package fr.esgi.baldwarsapi.domain.weapons.exceptions;

public class UserAlreadyOwnsWeaponException extends RuntimeException {

    public UserAlreadyOwnsWeaponException() {
        super("User already owns weapon.");
    }

}
