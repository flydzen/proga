public class HeapMax {
    private String[] Heap;
    private int size;

    public HeapMax(int maxsize)
    {
        this.size = 0;
        Heap = new String[maxsize + 1];
        Heap[0] = "";
    }

    private boolean isLeaf(int pos) {
        return pos >= (size / 2) && pos <= size;
    }

    private void swap(int fpos, int spos) {
        String tmp = Heap[fpos];
        Heap[fpos] = Heap[spos];
        Heap[spos] = tmp;
    }

    private void maxHeapify(int pos) {
        if (isLeaf(pos))
            return;

        if (Heap[pos].compareTo(Heap[pos*2]) > 0  || Heap[pos].compareTo(Heap[pos * 2 + 1]) > 0) {
            if (Heap[2 * pos].compareTo(Heap[pos * 2 + 1]) > 0) {
                swap(pos, pos * 2);
                maxHeapify(pos * 2);
            } else {
                swap(pos, pos * 2 + 1);
                maxHeapify(pos * 2 + 1);
            }
        }
    }

    public void insert(String element) {
        Heap[++size] = element;

        // Traverse up and fix violated property
        int cur = size;
        while (Heap[cur].compareTo(Heap[cur/2]) > 0) {
            swap(cur, cur/2);
            cur = cur/2;
        }
    }

    public String pop() {
        String popped = Heap[1];
        Heap[1] = Heap[size--];
        maxHeapify(1);
        return popped;
    }
}