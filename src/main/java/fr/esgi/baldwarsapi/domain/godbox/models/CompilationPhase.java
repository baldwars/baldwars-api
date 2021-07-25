package fr.esgi.baldwarsapi.domain.godbox.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CompilationPhase extends Phase {
    @JsonProperty("script")
    private final String script;

    public CompilationPhase(String name) {
        super(name);
        this.script = "cd game-engine\n" +
                "mkdir build\n" +
                "cd build\n" +
                "cmake ..\n" +
                "cmake --build .";
    }

    public String getScript() {
        return script;
    }
}
