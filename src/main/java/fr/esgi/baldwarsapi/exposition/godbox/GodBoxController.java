package fr.esgi.baldwarsapi.exposition.godbox;

import fr.esgi.baldwarsapi.domain.godbox.GodBoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/godbox")
@RequiredArgsConstructor
public class GodBoxController {

    private final GodBoxService godBoxService;

    @PostMapping("/run")
    public ResponseEntity<?> compileAndRun(@RequestBody UserCode userCode) {
        try {
            var response = godBoxService.runWithCompilation(userCode.getUsername(), userCode.getCode());
            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
