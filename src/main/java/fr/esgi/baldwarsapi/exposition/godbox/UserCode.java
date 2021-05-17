package fr.esgi.baldwarsapi.exposition.godbox;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserCode {
    private String username;
    private String code;
}
