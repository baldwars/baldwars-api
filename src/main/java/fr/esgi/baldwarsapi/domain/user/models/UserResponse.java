package fr.esgi.baldwarsapi.domain.user.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class UserResponse {
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private Integer eloPoints;
    private Integer baldCoins;
}