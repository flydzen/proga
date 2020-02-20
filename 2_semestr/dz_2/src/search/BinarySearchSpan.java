package search;

import java.util.ArrayList;
import java.util.List;

public class BinarySearchSpan {

    // Pre:: (n = a.length) _ ∀ i, j ∈ (0..n) a[i] >= a[j] && x ∈ (a[0]..a[n]) && ∃ i  a[i] <= x
    public static Integer lBinSearch(List<Integer> a, int x){
        // z = finding element
        int l = -1;
        int r = a.size();
        // z ∈ (l, r) && l < r
        while (l < r - 1) {
            // z ∈ (l, r]
            int m = (l+r)/2;
            // l < m < r && a[l] <= a[m], a[z] <= a[r] && m - m' = (r-l)/2
            if (a.get(m) > x){
                // m < z &&
                // && a[m] > x
                l = m;
                // l < z
            } else {
                // m > z && r > z && a[r] < x
                r = m;
                // r >= z &&
            }
            // (r-l)%2 == 0 |  r-l = 2(r'-l')
            // (r-l)%2 == 1 |  r-l = 2(r'
        }
        // z ∈ (l, r] && l = r - 1 && a[r] <= x
        return r;
    }
    // Post:: 0 <= r < n && a[r] <= x && a[r-1] > x && r = z


    // Pre:: (n = a.length) _ ∀ i, j ∈ (0..n) a[i] >= a[j] && x ∈ (a[0]..a[n]) && ∃ i  a[i] <= x
    public static Integer rBinSearch(List<Integer> a, int x){
        // z = finding element
        int l = -1;
        int r = a.size();
        // z ∈ (l, r) && l < r
        while (l < r - 1) {
            // z ∈ (l, r]
            int m = (l+r)/2;
            // l < m < r && a[l] <= a[m], a[z] <= a[r] && m - m' = (r-l)/2
            if (a.get(m) >= x){
                // m < z &&
                // && a[m] >= x
                l = m;
                // l < z
            } else {
                // m > z && r > z && a[r] < x
                r = m;
                // r >= z &&
            }
            // z ∈ (l, r]
            // (r-l)%2 == 0 |  r-l = 2(r'-l')
            // (r-l)%2 == 1 |  r-l = 2(r'
        }
        // z ∈ (l, r] && l = r - 1 && a[r] < x
        return r;
    }
    // Post:: 0 < r <= n && a[r] <= x && a[r-1] > x  && r = z

    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        // x = input;
        List<Integer> list = new ArrayList<>(args.length-1);
        // list = [];
        for (int i = 1; i < args.length; i++) {
            list.add(Integer.parseInt(args[i]));
        }
        // list = input;
        int rb = rBinSearch(list, x);
        // rb: arr[rb] < x, arr[rb-1] >= x
        int lb = lBinSearch(list, x);
        // lb: arr[lb] <= x, arr[i-1] > x
        //  7  6  5  4      4    3  2  1
        //  -  -  -  +      +    -  -  -
        //  0  1  2  r-2    r-1  r  6  7
        //           l      l+1
        // rb - lb = otvet
        System.out.println(lb + " " + (rb-lb));
    }
}


