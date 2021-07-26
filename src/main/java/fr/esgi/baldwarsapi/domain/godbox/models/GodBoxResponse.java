package fr.esgi.baldwarsapi.domain.godbox.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GodBoxResponse {

    @JsonProperty("phases")
    private List<PhaseResponse> data;
}