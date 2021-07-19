package fr.esgi.baldwarsapi.domain.analysis.plagiarism;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PlagiarismSimilarity {

    public Double winnowing(String s1, String s2) {
        var source1 = new PlagiarismTokenizer(s1);
        var source2 = new PlagiarismTokenizer(s2);

        var fingerprints1 = source1.getFingerprints();
        var fingerprints2 = source2.getFingerprints();

        return getSimilarity(fingerprints1, fingerprints2);
    }

    public Double getSimilarity(Set<Long> a, Set<Long> b) {
        var jaccard = jaccardSimilarity(a, b);
        var sorensen = sorensenSimilarity(a, b);
        var andberg = andbergSimilarity(a, b);

        var similarity = (jaccard + sorensen + andberg) / 3;

        return (double)Math.round(similarity * 100) / 100;
    }

    public Double jaccardSimilarity(Set<Long> a, Set<Long> b) {
        var union = getUnion(a, b);
        var intersection = getIntersection(a, b);
        var similarity = (double)intersection.size() / union.size() * 100;

        return (double)Math.round(similarity * 100) / 100;
    }

    public Double sorensenSimilarity(Set<Long> a, Set<Long> b) {
        var intersection = getIntersection(a, b);
        var similarity = (double)(2 * intersection.size()) / (a.size() + b.size()) * 100;

        return (double)Math.round(similarity * 100) / 100;
    }

    public Double andbergSimilarity(Set<Long> a, Set<Long> b) {
        var union = getUnion(a, b);
        var intersection = getIntersection(a, b);
        var symmetricDifference = getSymmetricDifference(a, b);

        var similarity = (double)intersection.size() / (union.size() + symmetricDifference.size()) * 100;

        return (double)Math.round(similarity * 100) / 100;
    }

    private Set<Long> getUnion(Set<Long> a, Set<Long> b) {
        var union = new HashSet<Long>();

        union.addAll(a);
        union.addAll(b);

        return union;
    }

    private Set<Long> getIntersection(Set<Long> a, Set<Long> b) {
        var intersection = new HashSet<Long>();
        var longest = a.size() > b.size() ? a : b;
        var smallest = a.size() < b.size() ? a : b;

        for (var value : longest) {
            if (smallest.contains(value)) {
                intersection.add(value);
            }
        }

        return intersection;
    }

    private Set<Long> getSymmetricDifference(Set<Long> a, Set<Long> b) {
        var symmetricDifference = getUnion(a, b);
        var intersection = getIntersection(a, b);

        symmetricDifference.removeAll(intersection);

        return symmetricDifference;
    }

}
