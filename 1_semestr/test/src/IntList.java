import java.io.BufferedReader;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collections;


public class IntList{
    private int[] array;
    private int size;
    private int last = 0;

    IntList(int size) {
        this.size = size;
        array = new int[1];
    }

    IntList() {
        this(1);
    }

    IntList(int[] nums) {
        this.size = nums.length;
        array = new int[size];
        System.arraycopy(nums, 0, array, 0, size);
    }


    void set(int index, int value) {
        array[index] = value;
    }

    void add(int value) {
        if (last == size) {
            array = upSize(array);
        }
        array[last++] = value;
    }

    public String toString(){
        if (last == 0){
            return "";
        }
        StringBuilder sb = new StringBuilder(size*2);
        for (int i = 0; i < last-1; i++){
            sb.append(array[i]);
            sb.append(" ");
        }
        sb.append(array[last-1]);
        return sb.toString();
    }

    int get(int index) {
        return array[index];
    }

    int size() {
        return size;
    }

    private int[] upSize(int[] ar) {
        int[] newCh = new int[size * 2];
        System.arraycopy(ar, 0, newCh, 0, size);
        size++;
        return newCh;
    }
}
