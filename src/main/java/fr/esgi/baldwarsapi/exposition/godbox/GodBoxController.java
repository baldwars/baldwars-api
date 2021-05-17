package fr.esgi.baldwarsapi.exposition.godbox;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/godbox")
@RequiredArgsConstructor
public class GodBoxController {

    private final GodBoxService service;

    @PostMapping("/run")
    public ResponseEntity<?> compileAndRun(@RequestBody UserCode userCode) {
        try {
            var response = service.runWithCompilation(userCode.getCode());
            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
