package sample;

public class TrieOfSynonyms extends Trie {

    TrieOfSynonyms () {
        super();
    }

    public void addString(String s, String synonym) {
        Trie currentNode = this;
        s = s.toLowerCase();
        int stringLength = s.length();
        for (int positionInString= 0; positionInString < stringLength; positionInString++) {
            int code = (int)s.charAt(positionInString) - (int)'a';
            if (currentNode.next[code] == null) {
                currentNode.next[code] = new TrieOfFrecuency();
            }
            currentNode = currentNode.next[code];
        }
        currentNode.ends = true;
        currentNode.synonym = synonym;
    }

}