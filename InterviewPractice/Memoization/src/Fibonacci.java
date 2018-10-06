import java.util.HashMap;

public class Fibonacci {

    public static void main(String[] args) {
        assert fib(7) == 13;
        assert fib(12) == 144;
    }

    public static HashMap<Integer, Integer> fibDict = new HashMap<>();

    public static int fib(int n) {
        try {
            return fibDict.get(n);
        }
        catch (Exception e) {
            if (n == 0) {
                fibDict.put(n, 0);
                return 0;
            }
            else if (n == 1) {
                fibDict.put(n, 1);
                return 1;
            }
            else {
                int res = fib(n - 1) + fib(n - 2);
                fibDict.put(n, res);
                return res;
            }
        }
    }
}
