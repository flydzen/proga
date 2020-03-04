import java.util.*;

public class e {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayDeque<Integer> q = new ArrayDeque<>();
        int n = sc.nextInt();
        int m = sc.nextInt();
        List<List<Integer>> ss = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ss.add(new ArrayList<>());
        }
        Set<Integer> sity = new HashSet<>();
        for (int i = 0; i < n - 1; i++) {
            int a = sc.nextInt() - 1;
            int b = sc.nextInt() - 1;
            ss.get(a).add(b);
            ss.get(a).add(a);
        }
        for (int i = 0; i < m; i++) {
            sity.add(sc.nextInt()-1);
        }
        q.add(0);
        List<Integer> w = new ArrayList<>(n);
        w.set(0, 1);
        int last = 0;
        int d = 0;
        while (!q.isEmpty()){
            int v = q.pop();
            int dad = w.get(v);
            if (dad > d){
                d = dad;
                last = v;
            }
            for(int i : ss.get(v)){
                if (w.get(i) == 0) {
                    q.add(i);
                    w.set(i, dad + 1);
                }
            }
        }
        if (d%2==0){
            System.out.println("NO");
            System.exit(0);
        }

        int start = last;
        int dad = d;
        while (dad != d/2+1){
            for (int i:ss.get(start)){
                if (w.get(i) < dad) {
                    start = i;
                    dad--;
                }
            }
        }
        q.add(start);
        w = new ArrayList<>(n);
        w.set(start, 1);
        int lenn = d/2;
        while (!q.isEmpty()){
            int v = q.pop();
            int weig = w.get(v);
            if (sity.){
                d = dad;
                last = v;
            }
            for(int i : ss.get(v)){
                if (w.get(i) == 0) {
                    q.add(i);
                    w.set(i, dad + 1);
                }
            }
        }
    }
}
