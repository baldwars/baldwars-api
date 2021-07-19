package fr.esgi.baldwarsapi.domain.analysis.plagiarism;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlagiarismService {

    private final PlagiarismSimilarity similarityService;

    public void analyse(UUID owner, String source) {

    }

    public static boolean isCheater(Double similarity) {
        return similarity >= 60.0;
    }
}
