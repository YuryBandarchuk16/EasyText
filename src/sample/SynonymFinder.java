package sample;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.ArrayList;

public class SynonymFinder {

    /*

    While iterating over the words from the text it sometimes might me impossible to find a word
    in local synonyms dictionary. Therefore "Thesaurus" API will be used to find the synonym.

     */

    private PrintWriter printWriter;
    private HashMapOfSynonyms hashMapOfSynonyms = null;
    private HashMapOfFrequency hashMapOfFrequency = null;
    private static final String REQUEST_URL = "http://thesaurus.altervista.org/thesaurus/v1?";
    private static final String MY_API_KEY = "zyQOWZsKlGVzWBGhc47S"; // "Thesaurus" API KEY

    SynonymFinder() throws IOException {
        hashMapOfFrequency = new HashMapOfFrequency();
        hashMapOfSynonyms = new HashMapOfSynonyms();
        loadFrecuency("resourses/freq.txt"); // 20 000 most frequent words based on Google research
        loadFrecuency("resourses/freq-usa.txt"); // 20 000 most frequent words using American spelling
        loadSynonyms("resourses/syn.txt");

    }

    private void upWrite(String fileName, String what) throws FileNotFoundException {
        printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName, true)));
        printWriter.println(what);
        printWriter.close();
    }

    public String getSynonym(String word) throws Exception {
        word = word.toLowerCase();
        boolean containtsThisWord = hashMapOfSynonyms.hasSynonymFor(word);
        if (!containtsThisWord) {
            return findSynonym(word);
        } else {
            return hashMapOfSynonyms.getSynonym(word);
        }
    }

    private String findSynonym(String word) throws Exception {
        String answer = null;
        int answerPriority = Integer.MAX_VALUE;
        int currentPriority = hashMapOfFrequency.getPriority(word);
        if (currentPriority != -1) {
            answer = word;
            answerPriority = currentPriority;
        }
        if (answerPriority <= 5000) {
            hashMapOfSynonyms.addString(word, answer);
            upWrite("resourses/syn.txt", word + "-" + answer);
            return word;
        }
        HTTPConnection httpConnection = new HTTPConnection(REQUEST_URL + "word=" + word + "&language=en_US&key=" + MY_API_KEY + "&output=json"); // make request to get some synonyms
        ArrayList<String> result = httpConnection.makeRequest();
        JSONParser parser = new JSONParser();
        for (String s : result) {
            JSONObject mainObject = (JSONObject)parser.parse(s);
            if (mainObject.containsKey("response") == false) {
                continue;
            }
            String myCategory = "#";
            JSONArray _array = (JSONArray)mainObject.get("response");
            for (Object _elemement : _array) {
                JSONObject firstElement = (JSONObject)_array.get(0);
                if (firstElement.containsKey("list") == false) {
                    continue;
                } else {
                    firstElement = (JSONObject)firstElement.get("list");
                }
                if (firstElement == null) {
                    continue;
                }
                String cat = null;
                if (firstElement.containsKey("category")) {
                    cat = (String)firstElement.get("category");
                }
                if (cat == null) {
                    continue;
                }
                if (myCategory.equals("#")) {
                    myCategory = cat;
                }
                if (!myCategory.equals(cat)) {
                    continue;
                }
                JSONObject element = firstElement;
                if (element.containsKey("category")) {
                    String value = (String)element.get("category");
                    if (value.equals(myCategory)) {
                        if (element.containsKey("synonyms")) {
                            String synValue = (String)element.get("synonyms");
                            String[] words;
                            StringBuilder sb = new StringBuilder();
                            for (int i = 0; i < synValue.length(); i++) {
                                if (synValue.charAt(i) == '|') {
                                    sb.append('-');
                                } else {
                                    sb.append(synValue.charAt(i));
                                }
                            }
                            synValue = sb.toString();
                            if (synValue.contains("-")) {
                                words = synValue.split("-");
                            } else {
                                words = new String[1];
                                words[0] = synValue;
                            }
                            for (String wordFromWords : words) {
                                String sss = modify(wordFromWords);
                                if (sss == null) {
                                    continue;
                                }
                                int myPriority = hashMapOfFrequency.getPriority(sss);
                                if (myPriority != -1) {
                                    if (myPriority < answerPriority) {
                                        answer = sss;
                                        answerPriority = myPriority;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (answer != null) {
            upWrite("resourses/syn.txt", word + "-" + answer);
        } else {
            answer = word;
        }
        hashMapOfSynonyms.addString(word, answer);
        return answer;
    }

    private String modify(String s) {
        if (s.contains("antonym")) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int index = 0; index < s.length(); index++) {
            char c = s.charAt(index);
            boolean f = (c >= 'a' && c <= 'z') | (c >= 'A' && c <= 'Z');
            if (f == false) {
                if (c == ' ' && index + 1 < s.length()) {
                    c = s.charAt(index + 1);
                    f = (c >= 'a' && c <= 'z') | (c >= 'A' && c <= 'Z');
                    if (f == true) {
                        return null;
                    }
                }
                if (c == '-') {
                    return null;
                }
                break;
            } else {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }

    private void loadSynonyms(String fileName) throws IOException {
        File file = new File(fileName);
        if (file.exists() == false) {
            return;
        }
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String nextWord;
        String[] words;
        while ((nextWord = bufferedReader.readLine()) != null) {
            if (!nextWord.contains("-")) {
                continue;
            }
            words = nextWord.split("-");
            hashMapOfSynonyms.addString(words[0], words[1]);
        }
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
                hashMapOfFrequency.addString(nextWord, currentPriority);
            }
        }
    }

    private boolean isFail(String a) {
        if (a.contains("-")) {
            return true;
        }
        for (int i = 0; i < a.length(); i++) {
            char c = a.charAt(i);
            boolean f = (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
            if (!f) {
                return true;
            }
        }
        return false;
    }

    public int addPair(String a, String b) throws FileNotFoundException {
        if (isFail(a)) {
            return 1;
        }
        if (isFail(b)) {
            return 1;
        }
        boolean containsThisString = hashMapOfSynonyms.hasSynonymFor(a);
        if (!containsThisString) {
            hashMapOfSynonyms.addString(a, b);
            upWrite("resourses/syn.txt", a + "-" + b);
            return 0;
        } else {
            return 2;
        }
    }
}
