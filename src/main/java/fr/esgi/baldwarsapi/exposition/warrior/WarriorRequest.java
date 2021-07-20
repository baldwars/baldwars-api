package fr.esgi.baldwarsapi.exposition.warrior;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class WarriorRequest {
    private final String name;
    private final UUID owner;
}