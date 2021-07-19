package fr.esgi.baldwarsapi.domain.scripts;

public class ScriptNotFoundException extends RuntimeException {

    public ScriptNotFoundException() {
        super("Script not found.");
    }

}
