package fr.esgi.baldwarsapi.domain.warrior;

public class WarriorAlreadyExistsException extends RuntimeException {
    public WarriorAlreadyExistsException() {
        super("This user already has a warrior");
    }
}
