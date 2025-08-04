public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        LinkedListDeque<Character> deque = new LinkedListDeque<>();
        for(int i = 0;i < word.length();i += 1) {
            char c = word.charAt(i);
            deque.addLast(c);
        }
        return deque;
    }

    public boolean isPalindrome(String word) {
        Deque d = wordToDeque(word);

        return recursivePalindrome(d);
    }

    private boolean recursivePalindrome(Deque d) {
        if(d.size() <= 1) {
            return true;
        }
        else {
            char a = (char)d.removeFirst();
            char b = (char)d.removeLast();
            if(a == b) {
                return recursivePalindrome(d);
            }
            else {
                return false;
            }
        }
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque d = wordToDeque(word);
        while(d.size() > 1) {
            char a = (char)d.removeFirst();
            char b = (char)d.removeLast();
            if(!cc.equalChars(a,b)) {
                return false;
            }
        }
        return true;
    }
}

