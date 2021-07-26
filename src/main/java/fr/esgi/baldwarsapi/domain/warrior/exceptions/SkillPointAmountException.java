package fr.esgi.baldwarsapi.domain.warrior.exceptions;

public class SkillPointAmountException extends RuntimeException {

    public SkillPointAmountException() {
        super("Skill points amount does not match.");
    }
}
