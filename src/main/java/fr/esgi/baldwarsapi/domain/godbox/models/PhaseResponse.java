package fr.esgi.baldwarsapi.domain.godbox.models;

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

    @JsonProperty("time")
    private Double time;

    @JsonProperty("time_wall")
    private Double timeWall;

    @JsonProperty("used_memory")
    private Long usedMemory;

    @JsonProperty("sandbox_status")
    private String sandboxStatus;

    @JsonProperty("csw_voluntary")
    private Integer cswVoluntary;

    @JsonProperty("csw_forced")
    private Integer cswForced;
}
