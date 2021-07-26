package fr.esgi.baldwarsapi.domain.analysis.plagiarism;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlagiarismTokenizer {

    private final String source;
    private final Integer kGram;

    public PlagiarismTokenizer(String source) {
        this.source = prepareContentToAnalysis(source);
        this.kGram = 7;
    }

    private String prepareContentToAnalysis(String str) {
        var s = str.replaceAll("[^a-zA-Z0-9]", "");
        return s.toLowerCase();
    }

    public List<Long> tokenize() {
        var words = new ArrayList<String>();

        for (int i = 0, end = kGram; end <= source.length(); i++, end = i + kGram) {
            words.add(source.substring(i, end));
        }

        var tokens = new ArrayList<Long>();
        long primary = 3L;
        for (var word : words) {
            var characters = word.toCharArray();
            long hash = 0;

            for (int i = 0; i < characters.length; i++) {
                hash += (long)Math.pow((long)characters[i] * primary, kGram - i);
            }

            tokens.add(hash);
        }

        return tokens;
    }

    public Set<Long> getFingerprints() {
        var fingerprints = new HashSet<Long>();
        var tokens = tokenize();

        for (int i = 0; i < tokens.size() - 1; i++) {
            fingerprints.add(Math.min(tokens.get(i), tokens.get(i + 1)));
        }

        return fingerprints;
    }

}
