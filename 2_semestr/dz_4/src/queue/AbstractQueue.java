package queue;

public abstract class AbstractQueue implements Queue {
    int size;


    // empty || not empty
    public boolean isEmpty() {
        return size == 0;
    }
    // empty || not empty, empty == 0 elements in dequeueue

    // any
    public int size() {
        return size;
    }
    // return number of elements in deque
}
