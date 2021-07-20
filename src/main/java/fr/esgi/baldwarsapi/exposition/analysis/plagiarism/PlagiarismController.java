package fr.esgi.baldwarsapi.exposition.analysis.plagiarism;

import fr.esgi.baldwarsapi.domain.analysis.plagiarism.PlagiarismScript;
import fr.esgi.baldwarsapi.domain.analysis.plagiarism.PlagiarismService;
import fr.esgi.baldwarsapi.domain.scripts.models.Script;
import fr.esgi.baldwarsapi.domain.user.UserNotFoundException;
import fr.esgi.baldwarsapi.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/plagiarism")
@RequiredArgsConstructor
public class PlagiarismController {

    private final UserService userService;
    private final PlagiarismService service;

    @PostMapping
    public ResponseEntity<List<PlagiarismScript>> analyse(@RequestBody Script script) {
        try {
            this.userService.findOneById(script.getOwner());
            var scripts = this.service.analyse(script);

            return ResponseEntity.ok(scripts);

        } catch (UserNotFoundException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

}
