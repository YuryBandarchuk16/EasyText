package sample;

import java.io.*;

public class SynonymFinder {

    /*

    While iterating over the words from the text it sometimes might me impossible to find a word
    in local synonyms dictionary. Therefore "Thesaurus" API will be used to find the synonym.

     */

    private TrieOfSynonyms trieOfSynonymsRoot = null;
    private TrieOfFrecuency trieOfFrecuencyRoot = null;
    private static final String MY_API_KEY = "zyQOWZsKlGVzWBGhc47S"; // "Thesaurus" API KEY

    SynonymFinder() throws IOException {
        trieOfFrecuencyRoot = new TrieOfFrecuency();
        trieOfSynonymsRoot = new TrieOfSynonyms();
        loadFrecuency("resourses/freq.txt"); // 20 000 most frequent words based on Google research
        loadFrecuency("resourses/freq-usa.txt"); // most frequent words using American spelling
    }

    private void loadFrecuency(String fileName) throws IOException {
        File file = new File(fileName);
        if (file.exists() == false) {
            return;
        }
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        for (int currentPriority = 1; ; currentPriority++) {
            String nextWord = bufferedReader.readLine();
            if (nextWord == null) {
                break;
            } else if (nextWord.length() > 0) {
                trieOfFrecuencyRoot.addString(nextWord, currentPriority);
            }
        }
    }

}
