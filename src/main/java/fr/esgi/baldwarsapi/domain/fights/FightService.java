package fr.esgi.baldwarsapi.domain.fights;

import fr.esgi.baldwarsapi.domain.fights.exceptions.CompilationErrorException;
import fr.esgi.baldwarsapi.domain.fights.exceptions.ExecutionErrorException;
import fr.esgi.baldwarsapi.domain.fights.models.FightResponse;
import fr.esgi.baldwarsapi.domain.fights.models.FightTestResponse;
import fr.esgi.baldwarsapi.domain.godbox.GodBoxService;
import fr.esgi.baldwarsapi.domain.godbox.models.GodBoxResponse;
import fr.esgi.baldwarsapi.domain.scripts.ScriptService;
import fr.esgi.baldwarsapi.domain.scripts.models.Script;
import fr.esgi.baldwarsapi.domain.user.UserService;
import fr.esgi.baldwarsapi.domain.user.mappers.UserMapper;
import fr.esgi.baldwarsapi.domain.user.models.User;
import fr.esgi.baldwarsapi.domain.user.models.UserResponse;
import fr.esgi.baldwarsapi.domain.warrior.models.Warrior;
import fr.esgi.baldwarsapi.domain.weapons.WeaponService;
import fr.esgi.baldwarsapi.domain.weapons.models.WeaponGame;
import fr.esgi.baldwarsapi.infrastructure.fights.FightRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FightService {

    private final FightMapper mapper;
    private final UserMapper userMapper;
    private final FightRepository repository;
    private final WeaponService weaponService;
    private final UserService userService;
    private final GodBoxService godBoxService;
    private final ScriptService scriptService;

    @SneakyThrows
    public FightTestResponse test(Script userScript, String testerName) {
        var opponentScript = findTesterScript(testerName);
        var phases = this.simulate(userScript, opponentScript).getData();

        if (phases.get(0).getStatus() != 0) {
            throw new CompilationErrorException(phases.get(0).getStderr());
        }

        if (phases.get(1).getStatus() != 0) {
            throw new ExecutionErrorException(phases.get(1).getStderr());
        }

        var fight = mapper.from(phases.get(1).getStdout());

        UserResponse winner = null;

        if (fight.getWinner() != null) {
            winner = userMapper.to(userService.findOneByWarrior(fight.getWinner()));
        }

        return new FightTestResponse(winner, fight);
    }

    @SneakyThrows
    public FightResponse fight(Script strikerScript, UUID opponent) {
        var opponentScript = this.scriptService.findUserDefenseScript(opponent);
        var phases = this.simulate(strikerScript, opponentScript).getData();

        if (phases.get(0).getStatus() != 0) {
            throw new CompilationErrorException(phases.get(0).getStderr());
        }

        if (phases.get(1).getStatus() != 0) {
            throw new ExecutionErrorException(phases.get(1).getStderr());
        }

        var fight = mapper.from(phases.get(1).getStdout());

        UUID winner = null;

        if (fight.getWinner() != null) {
            winner = userService.findOneByWarrior(fight.getWinner()).getId();
        }

        var entity = mapper.from(
                phases.get(1).getStdout(),
                strikerScript.getOwner(),
                opponent,
                winner);

        var inserted = this.repository.save(entity);

        if (winner != null) {
            this.userService.gainExperience(winner);
        }

        var striker = userMapper.to(userService.findOneById(strikerScript.getOwner()));
        var defender = userMapper.to(userService.findOneById(opponent));

        return new FightResponse(inserted.getId(), striker, defender, winner, fight);
    }

    public List<FightResponse> findUserFightsHistory(UUID userId) {
        return this.repository.findAll(Sort.by("created"))
                .stream()
                .filter(entity -> entity.getStriker().equals(userId)
                        || entity.getDefender().equals(userId))
                .map(mapper::from)
                .collect(Collectors.toList());
    }

    private GodBoxResponse simulate(Script userScript, Script opponentScript) {
        var striker = this.userService.findOneById(userScript.getOwner());
        var opponent = this.userService.findOneById(opponentScript.getOwner());

        var warriors = this.prepareWarriors(striker, opponent);
        var weapons = this.prepareWeapons(striker, opponent);

        this.godBoxService.prepareResourceFiles(warriors, weapons);
        this.godBoxService.prepareScriptFiles(userScript, opponentScript);

        return this.godBoxService.runWithCompilation();
    }

    private List<Warrior> prepareWarriors(User striker, User opponent) {
        var warriors = new ArrayList<Warrior>();
        warriors.add(striker.getWarrior());
        warriors.add(opponent.getWarrior());

        return warriors;
    }

    private List<List<WeaponGame>> prepareWeapons(User striker, User opponent) {
        var userWeapons = this.weaponService.findUserWeaponsToFight(striker.getId());
        var opponentWeapons = this.weaponService.findUserWeaponsToFight(opponent.getId());

        var weapons = new ArrayList<List<WeaponGame>>();
        weapons.add(userWeapons);
        weapons.add(opponentWeapons);

        return weapons;
    }

    private Script findTesterScript(String name) {
        var user = this.userService.findOneByUsername(name);
        return this.scriptService.findAllUserScripts(user.getId()).get(0);
    }
}
