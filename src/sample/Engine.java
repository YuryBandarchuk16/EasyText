package sample;

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

    public String update(String text) throws Exception {
        text += " ";
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
                }
                if (index + 1 == textLength) {
                    continue;
                }
                stringBuilder.append(symbol);
            }
        }
        return stringBuilder.toString();
    }

}