package fr.esgi.baldwarsapi.domain.godbox;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhaseResponse {

    @JsonProperty("name")
    private String name;

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("stdout")
    private String stdout;

    @JsonProperty("stderr")
    private String stderr;
}
