import java.io.IOException;
import java.util.*;

public class ReverseSum {
    public static void main(String[] args) throws IOException {
        Scan sc = new Scan(System.in);
        List<Integer> xSum = new ArrayList<>();
        List<Integer> ySum = new ArrayList<>();
        List<IntList> ar = new ArrayList<>();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.isEmpty()) {
                ar.add(new IntList());
                ySum.add(0);
                continue;
            }
            Scan lineSc = new Scan(line);
            int summ = 0;
            IntList nums = new IntList();
            int cou = 0;
            while (lineSc.hasNextInt()) {
                int num = lineSc.nextInt();
                nums.add(num);
                summ += num;
                if (xSum.size() > cou) {
                    xSum.set(cou, xSum.get(cou) + num);
                } else {
                    xSum.add(num);
                }
                cou++;
            }
            ar.add(nums);
            ySum.add(summ);

        }
        for (int i = 0; i < ar.size(); i++) {
            int lineSum = ySum.get(i);
            for (int j = 0; j < ar.get(i).size(); j++) {
                System.out.print(lineSum + xSum.get(j) - ar.get(i).get(j));
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}