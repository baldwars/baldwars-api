package fr.esgi.baldwarsapi.exposition.weapons;

import fr.esgi.baldwarsapi.domain.weapons.WeaponService;
import fr.esgi.baldwarsapi.domain.weapons.exceptions.NotEnoughBaldCoinsException;
import fr.esgi.baldwarsapi.domain.weapons.exceptions.UserAlreadyOwnsWeaponException;
import fr.esgi.baldwarsapi.domain.weapons.exceptions.WeaponNotFoundException;
import fr.esgi.baldwarsapi.domain.weapons.models.WeaponGame;
import fr.esgi.baldwarsapi.domain.weapons.models.WeaponStore;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/weapons")
@RequiredArgsConstructor
public class WeaponController {

    private final WeaponService service;

    @GetMapping("/store")
    public ResponseEntity<List<WeaponStore>> findStoreWeapons() {
        var weapons = this.service.findStoreWeapons();
        return ResponseEntity.ok(weapons);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<WeaponGame>> findUserWeapons(@PathVariable("id")UUID id) {
        var weapons = this.service.findUserWeapons(id);
        return ResponseEntity.ok(weapons);
    }

    @PostMapping("/store/purchase")
    public ResponseEntity<?> purchaseWeapon(@RequestBody PurchaseRequest request) {
        try {
            this.service.purchaseWeapon(request);
            return ResponseEntity.ok().build();

        } catch (UserAlreadyOwnsWeaponException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        } catch (WeaponNotFoundException | NotEnoughBaldCoinsException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> init() {
        this.service.init();
        return ResponseEntity.ok().build();
    }
}
