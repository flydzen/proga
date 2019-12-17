import java.util.*;

public class ReverseSum {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<Integer> xSum = new ArrayList<>();
        List<Integer> ySum = new ArrayList<>();
        List<Integer[]> ar = new ArrayList<>();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.isEmpty()) {
                ar.add(new Integer[0]);
                ySum.add(0);
                continue;
            }
            int[] nums = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
            int summ = 0;
            for (int i = 0; i < nums.length; i++) {
                summ += nums[i];
                if (xSum.size() > i) {
                    xSum.set(i, xSum.get(i) + nums[i]);
                } else {
                    xSum.add(nums[i]);
                }
            }
            Integer[] numsInt = new Integer[nums.length];
            for (int i = 0; i < nums.length; i++) {
                numsInt[i] = nums[i];
            }

            ar.add(numsInt);
            ySum.add(summ);
        }
        for (int i = 0; i < ar.size(); i++) {
            int lineSum = ySum.get(i);
            for (int j = 0; j < ar.get(i).length; j++) {
                System.out.print(lineSum + xSum.get(j) - ar.get(i)[j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}