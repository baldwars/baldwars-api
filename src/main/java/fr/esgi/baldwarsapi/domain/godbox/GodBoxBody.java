package fr.esgi.baldwarsapi.domain.godbox;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GodBoxBody {

    @JsonProperty("compile_script")
    private String compileScript;

    @JsonProperty("run_script")
    private String runScript;

    @JsonProperty("files")
    private String filesBase64;

    public GodBoxBody(String username, String filesBase64) {
        this.compileScript = "/usr/local/gcc-11.1.0/bin/gcc " + username + "/main.c -o out";
        this.runScript = "./out";
        this.filesBase64 = filesBase64;
    }
}
