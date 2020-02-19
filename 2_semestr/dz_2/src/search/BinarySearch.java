package search;

import java.util.ArrayList;
import java.util.List;

public class BinarySearch {
    // Pre:: (n = a.length) _ ∀ i, j ∈ (0..n) a[i] >= a[j] && ∃ i  a[i] <= x

    public static int rBinSearch(List<Integer> a, int x) {
        return rBinSearch(a, x, -1, a.size());
    }

    // Pre:: (n = a.length) _ ∀ i, j ∈ (0..n) a[i] >= a[j] && ∃ i  a[i] <= x
    // l > -1            x ∈ (a[l]..a[n])
    // r < n             x ∈ (a[0]..a[r])
    // l > -1 && r < n   x ∈ (a[l]..a[n])

    public static int rBinSearch(List<Integer> a, int x, int l, int r){
        // z = finding element
        // l < z <= x
        if (l >= r - 1)
            // l-r+1 >= 0
            // r-l <= 1
            return r;
        // l < z < x
        int m = (l+r)/2;
        // l < m < r && m =  && a[l] <= a[m], a[z] <= a[r] && m - m' = (r-l)/2
        if (a.get(m) > x) {
            // l < m < z && a[m] > x
            // a[l] < x <= a[r]
            // (r-l)%2 == 0 |  r-l = 2(r'-l')
            // (r-l)%2 == 1 |  r-l = 2(r'
            return rBinSearch(a, x, m, r);
        } else {
            // l < m < z && a[m] <= x
            // a[l] < x <= a[r]
            // (r-l)%2 == 0 |  r-l = 2(r'-l')
            // (r-l)%2 == 1 |  r-l = 2(r'
            return rBinSearch(a, x, l, m);
        }
    }
    // Post:: a[l] < a[z] = a[r] && a[r] <= x


    // Pre:: (n = a.length) _ ∀ i, j ∈ (0..n) a[i] >= a[j] && x ∈ (a[0]..a[n]) && ∃ i  a[i] <= x
    public static Integer binSearch(List<Integer> a, int x){
        // z = finding element
        int l = -1;
        int r = a.size();
        // z ∈ (l, r)
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
    // Post:: 0 <= r < n && a[r] <= x && a[r-1] > x

    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        // x = input;
        List<Integer> list = new ArrayList<>(args.length-1);
        // list = [];
        for (int i = 1; i < args.length; i++) {
            list.add(Integer.parseInt(args[i]));
        }
        // list = input;
        System.out.println(binSearch(list, x));
    }
}


