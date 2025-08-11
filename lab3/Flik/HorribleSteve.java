public class HorribleSteve {
    public static void main(String[] args) {
        for (int i = 0; i < 500; i++) {
            Integer a = i;
            Integer b = i;
            if (!Flik.isSameNumber(a, b)) {
                System.out.println("i = " + i + " is not the same as b = " + b + "??");
            }
        }
    }
}
