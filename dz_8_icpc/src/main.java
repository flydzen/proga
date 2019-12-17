import java.util.*;

public class main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for(int _t = 0; _t < t; _t++){
            int n = sc.nextInt();
            List<Integer> a = new ArrayList<>();
            for( int _i = 0; _i < n; _i++) {
                a.add(sc.nextInt());
            }
            Map<Integer, Integer> c = new HashMap<>();
            int cou = 0;
            for(int j = n-2; j > 0; j--) {
                c.put(a.get(j+1), c.getOrDefault(a.get(j+1), 0)+1);
                for (int i = 0; i < j; i++) {
                    int key = 2*a.get(j) - a.get(i);
                    int value = c.getOrDefault(key, 0);
                    cou += value;
                }
            }
            System.out.println(cou);
        }
    }
}
