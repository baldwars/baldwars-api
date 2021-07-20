package fr.esgi.baldwarsapi.domain.scripts.exceptions;

public class ScriptAlreadyExistsException extends RuntimeException {

    public ScriptAlreadyExistsException() {
        super("Script already exists.");
    }

}
