package fr.esgi.baldwarsapi.domain.godbox;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SandBoxSettings {

    @JsonProperty("run_time_limit")
    private final Integer runTimeLimit;

    @JsonProperty("wall_time_limit")
    private final Integer wallTimeLimit;
}
