package sample;

public class TrieOfFrecuency extends Trie {

    TrieOfFrecuency () {
        super();
    }

    public void addString(String s, int priority) {
        Trie currentNode = this;
        s = s.toLowerCase(); // as the case does not matter all string are added in lower case
        int stringLength = s.length();
        for (int positionInString = 0; positionInString < stringLength; positionInString++) {
            int code = (int)s.charAt(positionInString) - (int)'a';
            if (currentNode.next[code] == null) {
                currentNode.next[code] = new TrieOfFrecuency();
            }
            currentNode = currentNode.next[code];
        }
        currentNode.ends = true;
        currentNode.priority = Math.min(currentNode.priority, priority);
    }

}
