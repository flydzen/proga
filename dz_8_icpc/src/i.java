import java.util.*;

public class i {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        List<Integer> obsX = new ArrayList<>();
        List<Integer> obsY = new ArrayList<>();
        List<Integer> obsH = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            obsX.add(sc.nextInt());
            obsY.add(sc.nextInt());
            obsH.add(sc.nextInt());
        }
        int xl = Integer.MAX_VALUE;
        int xr = Integer.MIN_VALUE;
        int yl = Integer.MAX_VALUE;
        int yr = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++){
            int h = obsH.get(i);
            if (obsX.get(i)-h < xl){
                xl = obsX.get(i)-h;
            }
            if (obsX.get(i)+h > xr){
                xr = obsX.get(i)+h;
            }
            if (obsY.get(i)-h < yl){
                yl = obsY.get(i)-h;
            }
            if (obsY.get(i)+h > yr){
                yr = obsY.get(i)+h;
            }
        }
        // System.out.println(xl +" " +  xr + " " + yl + " " + yr);
        int h = (int)Math.ceil((double)Math.max(xr-xl, yr-yl)/2);
        int x = (xl + xr)/2;
        int y = (yl + yr)/2;
        System.out.print(x);
        System.out.print(" ");
        System.out.print(y);
        System.out.print(" ");
        System.out.print(h);
    }
}
