import java.util.ArrayList;
import java.util.Scanner;
public class Reverse {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer[]> ar = new ArrayList<Integer[]>();
        while(sc.hasNextLine()){
            int[] nums = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
            Integer[] numsInt = new Integer[nums.length];
            for(int i = 0; i < nums.length; i++){
                numsInt[i] = new Integer(nums[i]);
            }
            ar.add(numsInt);
        }
        for (int i = ar.size() - 1; i >= 0; i--){
            for (int j = ar.get(i).length-1; j >= 0; j--){
                System.out.print(ar.get(i)[j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}