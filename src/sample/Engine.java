package sample;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Engine {

    private SynonymFinder synonymFinder;

    Engine() throws IOException {
        synonymFinder = new SynonymFinder();
    }

    private enum LetterType {
        UPPER, LOWER
    }

    private boolean isAlpha(char c) {
        return ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'));
    }

    private boolean isUpper(char c) {
        return (c >= 'A' && c <= 'Z');
    }

    private char toUpper(char c) {
        if (c >= 'A' && c <= 'Z') return c;
        int id = (int)c - (int)'a';
        return (char)(id + (int)'A');
    }

    private char toLower(char c) {
        if (c >= 'a' && c <= 'z') return c;
        int id = (int)c - (int)'A';
        return (char)(id + (int)'a');
    }

    public String toLower(String s) {
        StringBuilder stringBuilder = new StringBuilder();
        int stringLength = s.length();
        for (int index = 0; index < stringLength; index++) {
            stringBuilder.append(toLower(s.charAt(index)));
        }
        return stringBuilder.toString();
    }

    public int addPairOfSynonyms(String a, String b) throws FileNotFoundException {
        return synonymFinder.addPair(a, b);
    }

    public String getSynonym(String word) throws Exception {
        return synonymFinder.getSynonym(word);
    }

    public void removeSynonymForWord(String word) {
        synonymFinder.removeSynonymForWord(word);
    }

    public String update(String text, boolean alignment) throws Exception {
        text += " ";
        int buffer = 0;
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder currentWord = new StringBuilder();
        int textLength = text.length();
        for (int index = 0; index < textLength; index++) {
            char symbol = text.charAt(index);
            if (isAlpha(symbol)) {
                currentWord.append(symbol);
            } else {
                if (currentWord.length() > 0) {
                    String toSend = currentWord.toString();
                    currentWord.setLength(0);
                    int length = toSend.length();
                    LetterType firstLetterType = LetterType.LOWER;
                    if (isUpper(toSend.charAt(0))) {
                        firstLetterType = LetterType.UPPER;
                    }
                    toSend = synonymFinder.getSynonym(toSend);
                    if (firstLetterType == LetterType.UPPER) {
                        toSend = toUpper(toSend.charAt(0)) + toSend.substring(1);
                    }
                    stringBuilder.append(toSend);
                    buffer += toSend.length();
                }
                if (index + 1 == textLength) {
                    continue;
                }
                stringBuilder.append(symbol);
                buffer++;
                if (buffer >= 45 && alignment) {
                    stringBuilder.append("\n");
                    buffer = 0;
                }
            }
        }
        return stringBuilder.toString();
    }

}
