import java.util.Scanner;

public class b {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int i = -710*25000;
        int stop = i+(n*710);
        for (; i < stop; i+=710){
            System.out.println(i);
        }
    }
}
