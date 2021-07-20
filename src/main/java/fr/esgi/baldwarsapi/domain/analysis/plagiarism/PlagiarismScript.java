package fr.esgi.baldwarsapi.domain.analysis.plagiarism;

import fr.esgi.baldwarsapi.domain.scripts.models.Script;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlagiarismScript {
    private Double similarity;
    private Script script;
}
