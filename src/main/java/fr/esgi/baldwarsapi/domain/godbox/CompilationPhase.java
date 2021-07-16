package fr.esgi.baldwarsapi.domain.godbox;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CompilationPhase extends Phase {
    @JsonProperty("script")
    private final String script;

    public CompilationPhase(String name, String username) {
        super(name);
        this.script = "mkdir build\n" +
                "cd build\n" +
                "cmake ../src\n" +
                "cmake --build .";
//        this.script = "/usr/local/gcc-11.1.0/bin/gcc src/main.c -o out";
//        this.script = "/usr/local/gcc-11.1.0/bin/gcc " + username + "/main.c -o out";
    }

    public String getScript() {
        return script;
    }
}
