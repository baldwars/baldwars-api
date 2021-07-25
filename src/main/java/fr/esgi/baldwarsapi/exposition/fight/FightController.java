package fr.esgi.baldwarsapi.exposition.fight;

import fr.esgi.baldwarsapi.domain.fights.FightService;
import fr.esgi.baldwarsapi.domain.godbox.GodBoxService;
import fr.esgi.baldwarsapi.domain.scripts.ScriptService;
import fr.esgi.baldwarsapi.domain.scripts.models.Script;
import fr.esgi.baldwarsapi.domain.user.UserNotFoundException;
import fr.esgi.baldwarsapi.domain.user.UserService;
import fr.esgi.baldwarsapi.domain.warrior.models.Warrior;
import fr.esgi.baldwarsapi.domain.weapons.WeaponService;
import fr.esgi.baldwarsapi.domain.weapons.models.WeaponGame;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/fights")
@RequiredArgsConstructor
public class FightController {

    private final FightService service;

    @PostMapping("/test")
    public ResponseEntity<?> simulate(@RequestBody Script script) {
        return null;
//        try {
//            var user = this.userService.findOneById(script.getOwner());
//            var opponent = this.userService.findOneById(script.getOwner());
//
//            var warriors = new ArrayList<Warrior>();
//            warriors.add(user.getWarrior());
//            opponent.getWarrior().setId(1000);
//            warriors.add(opponent.getWarrior());
//
//            var userWeapons = this.weaponService.findUserWeaponsToFight(user.getId());
//            var opponentWeapons = this.weaponService.findUserWeaponsToFight(opponent.getId());
//
//            var weapons = new ArrayList<List<WeaponGame>>();
//            weapons.add(userWeapons);
//            weapons.add(opponentWeapons);
//            this.godBoxService.prepareResourceFiles(warriors, weapons);
//            this.godBoxService.prepareScriptFiles(script, script);
//
//            var response = this.godBoxService.runWithCompilation();
//
//            return ResponseEntity.ok(response);
//
//        } catch (UserNotFoundException exception) {
//            return ResponseEntity.badRequest().build();
//        }
    }
}
