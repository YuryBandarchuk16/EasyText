package sample;

import java.util.HashMap;

public class HashMapOfSynonyms {

    // After some tests I figured out that using HashMap is better that using Trie Data Structure

    private HashMap<String, String> synonyms;

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
}
