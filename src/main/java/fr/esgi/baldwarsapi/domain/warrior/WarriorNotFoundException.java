package fr.esgi.baldwarsapi.domain.warrior;

public class WarriorNotFoundException extends RuntimeException {
    public WarriorNotFoundException() {
        super("Warrior not found.");
    }
}
