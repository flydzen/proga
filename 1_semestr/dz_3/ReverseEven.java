import java.util.ArrayList;
import java.util.Scanner;
public class ReverseEven {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<String[]> ar = new ArrayList<String[]>();
        while(sc.hasNextLine()){
            ar.add(sc.nextLine().split(" "));
        }
        for (int i = ar.size() - 1; i >= 0; i--){
            for (int j = ar.get(i).length-1; j >= 0; j--){
                String temp = ar.get(i)[j];
                if (temp.length() > 0) {
                    int num = Character.getNumericValue(temp.charAt(temp.length() - 1));
                    if (num % 2 == 0) {
                        System.out.print(ar.get(i)[j]);
                        System.out.print(" ");
                    }
                }
            }
            System.out.println();
        }
    }
}