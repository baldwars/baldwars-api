package fr.esgi.baldwarsapi.domain.fights;

import fr.esgi.baldwarsapi.domain.godbox.GodBoxService;
import fr.esgi.baldwarsapi.domain.godbox.models.GodBoxResponse;
import fr.esgi.baldwarsapi.domain.scripts.models.Script;
import fr.esgi.baldwarsapi.domain.user.UserService;
import fr.esgi.baldwarsapi.domain.warrior.models.Warrior;
import fr.esgi.baldwarsapi.domain.weapons.WeaponService;
import fr.esgi.baldwarsapi.domain.weapons.models.WeaponGame;
import fr.esgi.baldwarsapi.infrastructure.fights.FightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FightService {

    private final FightRepository repository;
    private final WeaponService weaponService;
    private final UserService userService;
    private final GodBoxService godBoxService;

    public GodBoxResponse simulate(Script script) {
        var user = this.userService.findOneById(script.getOwner());
        var opponent = this.userService.findOneById(script.getOwner());

        var warriors = new ArrayList<Warrior>();
        warriors.add(user.getWarrior());
        opponent.getWarrior().setId(1000);
        warriors.add(opponent.getWarrior());

        var userWeapons = this.weaponService.findUserWeaponsToFight(user.getId());
        var opponentWeapons = this.weaponService.findUserWeaponsToFight(opponent.getId());

        var weapons = new ArrayList<List<WeaponGame>>();
        weapons.add(userWeapons);
        weapons.add(opponentWeapons);
        this.godBoxService.prepareResourceFiles(warriors, weapons);
        this.godBoxService.prepareScriptFiles(script, script);

        var response = this.godBoxService.runWithCompilation();

        return response;
    }
}
