package fr.esgi.baldwarsapi.domain.scripts.exceptions;

public class ScriptNotFoundException extends RuntimeException {

    public ScriptNotFoundException() {
        super("Script not found.");
    }

}
