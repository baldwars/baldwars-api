package fr.esgi.baldwarsapi.exposition.godbox;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class GodBoxResponse {

    @JsonProperty("compile_status")
    private Integer compileStatus;

    @JsonProperty("compile_stdout")
    private String compileStdOut;

    @JsonProperty("compile_stderr")
    private String compileStdErr;

    @JsonProperty("run_status")
    private Integer runStatus;

    @JsonProperty("run_stdout")
    private String runStdOut;

    @JsonProperty("run_stderr")
    private String runStdErr;

    @JsonProperty("profiling_result")
    private String profilingResult;
}