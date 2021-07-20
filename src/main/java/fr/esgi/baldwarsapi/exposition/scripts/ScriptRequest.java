package fr.esgi.baldwarsapi.exposition.scripts;

import lombok.Data;

import java.util.UUID;

@Data
public class ScriptRequest {

    private UUID owner;
    private String name;
    private String content;

}
