public class OffByN implements CharacterComparator{
    int n;
    @Override
    public boolean equalChars(char x, char y) {
        int diff = x - y;
        if(diff == n || diff == -n) {
            return true;
        }
        else return false;
    }

    public OffByN(int N) {
        n = N;
    }
}
