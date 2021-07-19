package fr.esgi.baldwarsapi.domain.godbox;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExecutionPhase extends Phase {

    @JsonProperty("script")
    private final String script;

    public ExecutionPhase(String name, String script) {
        super(name);
        this.script = "cd game-engine\n" +
                "cd build\n" +
                "./BaldWars_Game";
//        this.script = "./" + script;
    }

    public String getScript() {
        return script;
    }
}