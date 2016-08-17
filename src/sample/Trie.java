package sample;

/*

https://en.wikipedia.org/wiki/Trie

Trie is a very convenient data structure for finding and adding words.

It works in O(|WORD|) to add, and in O(|WORD|) to find the word WORD in trie. (|WORD| is the length of the word)

 */

public class Trie {

    protected Trie[] next;
    protected boolean ends;
    protected int priority;
    protected String synonym;

    Trie () {
        ends = false;
        synonym = null;
        priority = Integer.MAX_VALUE;
        next = new Trie[26];
        for (int i = 0; i < 26; i++) {
            next[i] = null;
        }
    }

    /*
     The function below will return the TrieNode where String s ends starting from the current node.
     In case the trie does not contain the String s starting from the current node the function below will return null.
    */
    public Trie getEndingNode(String s) { // will be called only from the root of the trie
        Trie currentNode = this;
        s = s.toLowerCase(); // the case of letters does not matter, therefore all words are added in lower case
        int stringLength = s.length();
        for (int positionInString = 0; positionInString < stringLength; positionInString++) {
            int code = (int)s.charAt(positionInString) - (int)'a';
            if (currentNode.next[code] == null) { // if we don't have the edge with needed code then return null
                return null;
            } else { // otherwise we have to go through this edge
                currentNode = currentNode.next[code];
            }
        }
        return currentNode;
    }

    // Check whether trie contains String s or not
    public boolean contains(String s) { // will be called only from the root of the trie
        Trie node = this.getEndingNode(s);
        if (node == null || node.ends == false) {
            return false;
        }
        return true;
    }

    /*
     The function below will add the String s to the Trie starting from the current TrieNode
    */
    public void addString(String s) {
        Trie currentNode = this;
        s.toLowerCase(); // the case of letters does not matter, therefore all words are added in lower case
        int stringLength = s.length();
        for (int positionInString = 0; positionInString < stringLength; positionInString++) {
            int code = (int)s.charAt(positionInString) - (int)'a';
            // if we don't have the edge with needed code we have to create the vertex and go through this edge
            if (currentNode.next[code] == null) {
                currentNode.next[code] = new Trie();
            }
            // then just go through the edge
            currentNode = currentNode.next[code];
        }
        currentNode.ends = true; // we have to mark this as some string ended here
    }


}
