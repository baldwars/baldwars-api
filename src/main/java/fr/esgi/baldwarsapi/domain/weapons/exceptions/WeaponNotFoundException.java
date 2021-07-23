package fr.esgi.baldwarsapi.domain.weapons.exceptions;

public class WeaponNotFoundException extends RuntimeException {

    public WeaponNotFoundException() {
        super("Weapon not found.");
    }

}
