package fr.esgi.baldwarsapi.domain.warrior.exceptions;

public class WarriorNotFoundException extends RuntimeException {
    public WarriorNotFoundException() {
        super("Warrior not found.");
    }
}
