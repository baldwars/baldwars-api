package fr.esgi.baldwarsapi.domain.weapons.exceptions;

public class NotEnoughBaldCoinsException extends RuntimeException {

    public NotEnoughBaldCoinsException() {
        super("User has not enough bald coins.");
    }
}
