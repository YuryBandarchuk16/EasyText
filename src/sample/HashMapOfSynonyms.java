package sample;

import com.sun.tools.javac.comp.Enter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HashMapOfSynonyms {

    // After some tests I figured out that using HashMap is better that using Trie Data Structure

    private HashMap<String, String> synonyms = null;

    HashMapOfSynonyms() {
        synonyms = new HashMap<>();
    }

    public void addString(String stringToadd, String itsSynonym) {
        if (!synonyms.containsKey(stringToadd)) {
            synonyms.put(stringToadd, itsSynonym);
        }
    }

    public boolean hasSynonymFor(String word) {
        return synonyms.containsKey(word);
    }

    public String getSynonym(String word) {
        return synonyms.get(word);
    }

    public void removeSynonymForWord(String word) {
        synonyms.remove(word);
    }

    public ArrayList<String> getAllSynonyms() { // used for rebuilding the file
        ArrayList<String> answer = new ArrayList<>();
        for (Map.Entry<String, String> element : synonyms.entrySet()) {
            answer.add(element.getKey() + "-" + element.getValue());
        }
        return answer;
    }
}
