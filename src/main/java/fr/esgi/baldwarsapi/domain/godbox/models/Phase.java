package fr.esgi.baldwarsapi.domain.godbox.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public abstract class Phase {

    @JsonProperty("name")
    private final String name;
}
