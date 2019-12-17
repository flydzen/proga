import java.util.*;

public class ReverseSum {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<Integer> xSum = new ArrayList<>();
        List<Integer> ySum = new ArrayList<>();
        List<List<Integer>> ar = new ArrayList<>();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            Scanner lineSc = new Scanner(line);
            if (!lineSc.isEmpty()) {
                int summ = 0;
                List<Integer> nums = new ArrayList<>();
                while (lineSc.hasNextInt()) {
                    Scanner numsSC = new Scanner(lineSc);
                    while (numsSc.hasNextInt()) {
                        int num = numsSc.nextInt();
                        nums.add(num);
                        summ += num;
                        if (xSum.size() > i) {
                            xSum.set(i, xSum.get(i) + num);
                        } else {
                            xSum.add(num);
                        }
                    }
                }
                ar.add(nums);
                ySum.add(summ);
            }
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