package fr.esgi.baldwarsapi.domain.analysis.plagiarism.visitors;

public class LocalizedElement {

    public String value;
    public Integer row;
    public String parent;

    public LocalizedElement(String value, Integer row, String parent) {
        this.value = value;
        this.row = row;
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "LocalizedElement{" +
                "value='" + value + '\'' +
                ", row=" + row +
                ", parent=" + parent +
                '}';
    }
}
