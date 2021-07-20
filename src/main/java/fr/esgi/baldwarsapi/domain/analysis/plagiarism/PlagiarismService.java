package fr.esgi.baldwarsapi.domain.analysis.plagiarism;

import fr.esgi.baldwarsapi.domain.analysis.plagiarism.visitors.TransformerVisitor;
import fr.esgi.baldwarsapi.domain.scripts.ScriptService;
import fr.esgi.baldwarsapi.domain.scripts.models.Script;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlagiarismService {

    private final ScriptService scriptService;
    private final PlagiarismSimilarity similarityService;

    public List<Script> analyse(Script source) {
        var scripts = this.scriptService.findAll();
        var userScripts = this.scriptService.findAllUserScripts(source.getOwner());

        scripts.removeAll(userScripts);

        var sourceContent = new TransformerVisitor(source.getContent()).visit();
        var similarScripts = new ArrayList<Script>();

        for (var script : scripts) {
            var scriptContent = new TransformerVisitor(script.getContent()).visit();
            var similarity = this.similarityService.winnowing(sourceContent, scriptContent);

            if (isCheater(similarity)) {
                similarScripts.add(script);
            }
        }

        return similarScripts;
    }

    public static boolean isCheater(Double similarity) {
        return similarity >= 60.0;
    }
}
