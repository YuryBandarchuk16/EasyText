package sample;

import java.util.HashMap;

public class HashMapOfFrequency {

    // After some tests I figured out that using HashMap is better that using Trie Data Structure

    private HashMap<String, Integer> frequency;

    HashMapOfFrequency() {
        frequency = new HashMap<>();
    }

    public void addString(String stringToadd, int priority) {
        if (!frequency.containsKey(stringToadd)) {
            frequency.put(stringToadd, priority);
        }
    }

    public int getPriority(String key) {
        if (!frequency.containsKey(key)) {
            return -1;
        } else {
            return frequency.get(key);
        }
    }

}
