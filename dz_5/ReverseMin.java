import java.util.*;
import java.io.*;

public class ReverseMin {

    public static class Line{
        private List<Integer> a;
        long sum;
        private int pos;

        Line(String s, int pos) throws IOException {
            a = new ArrayList<>();
            if (s.length() != 0) {
                Scan sc = new Scan(s);
                while (sc.hasNextInt()) {
                    int num = sc.nextInt();
                    a.add(num);
                    sum += num;
                }
                Collections.sort(a);
                Collections.reverse(a);
            }
            this.pos = pos;
        }
        List<Integer> getArray() {
            return a;
        }
    }

    public static void main(String[] args) throws IOException {
        List<Line> arr = new ArrayList<>();
        Scan sc = new Scan(System.in);
        while (sc.hasNextLine()) {
            arr.add(new Line(sc.nextLine(), arr.size()));
        }
        sc.close();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        Comparator<Line> comparator = (o1, o2) -> {
            if (o1.sum == o2.sum) {
                return o2.pos - o1.pos;
            } else {
                return Long.compare(o2.sum, o1.sum);
            }
        };
        arr.sort(comparator);
        for (Line j : arr) {
            List<Integer> curValues = j.getArray();
            for (int i : curValues) {
                writer.write(i + " ");
            }
            writer.newLine();
        }
        writer.close();
    }
}
