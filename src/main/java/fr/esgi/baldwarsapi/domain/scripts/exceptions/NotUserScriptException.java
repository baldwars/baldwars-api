package fr.esgi.baldwarsapi.domain.scripts.exceptions;

public class NotUserScriptException extends RuntimeException {

    public NotUserScriptException() {
        super("Script does not belong to user");
    }

}
