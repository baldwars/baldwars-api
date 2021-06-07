package fr.esgi.baldwarsapi.domain.godbox;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GodBoxBody {
    @JsonProperty("phases")
    private final List<Phase> phases;

    @JsonProperty("files")
    private String filesBase64;

    public GodBoxBody(String username, String filesBase64) {
        this.phases = new ArrayList<>();
        var compilation = new CompilationPhase(
                "Compilation",
                username);

        var execution = new ExecutionPhase("Execution", "out");
        this.phases.add(compilation);
        this.phases.add(execution);
        this.filesBase64 = filesBase64;
    }
}
