package fr.esgi.baldwarsapi.domain.godbox.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExecutionPhase extends Phase {

    @JsonProperty("script")
    private final String script;

    public ExecutionPhase(String name) {
        super(name);
        this.script = "cd game-engine\n" +
                "cd build\n" +
                "./BaldWars_Game";
    }

    public String getScript() {
        return script;
    }
}
