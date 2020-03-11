package queue;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

public class ArrayQueue extends AbstractQueue{
    private final int BASE_CAPACITY = 3;
    private int begin = 0;
    private static int cou = 0;
    private Object[] elements = new Object[BASE_CAPACITY];

    public void enqueue_(Object element) {
        if (size == capacity()) {
            ensureCapacity(capacity() * 2);
        }
        elements[end()] = element;
    }

    public void dequeue_() {
        elements[begin] = null;
        begin = (begin+1)%capacity();
    }

    public Object element_() {
        return elements[begin];
    }

    //capacity > size
    private Object[] copy(int capacity){
        Object[] temp = new Object[capacity];
        if (end() > begin){
            System.arraycopy(elements, begin, temp, 0, size);
        } else {
            System.arraycopy(elements, begin, temp, 0, capacity()-begin);
            System.arraycopy(elements, 0, temp, capacity()-begin, end());
        }
        return temp;
    }
    // temp = [begin..end, null...null), size(temp) = capacity

    // capacity > deque.length;
    private void ensureCapacity(int capacity) {
        if (capacity > capacity()) {
            elements = copy(capacity);
            begin = 0;
        }
    }
    // deque.length' > deque.length && elements = [begin..end, null...null)

    // any
    private int capacity(){
        return elements.length;
    }
    // return current maxsize of deque

    // any
    private int end(){
        return (begin + size)%capacity();
    }
    // end == begin && size=capacity || elements[end] = null
    // size == 0 || elements[(end-1 + capacity)%capacity] !=null

    public void reset(){
        cou = begin;
    }

    public Object getNext(){
        Object data = elements[cou];
        cou = (cou+1)%capacity();
        return data;
    }

    public Queue getQueue(){
        return new ArrayQueue();
    }


    public void push_(Object element){
        if (size == capacity()) {
            ensureCapacity(capacity() * 2);
        }
        begin = (begin -1 + capacity())%capacity();
        elements[begin] = element;
    }

    public Object peek_(){
        return elements[(begin+size-1)%capacity()];
    }

    public void remove_() {
        elements[(begin+size)%capacity()] = null;
    }


    public ArrayQueue makeCopy() {
        final ArrayQueue newQ = new ArrayQueue();
        newQ.elements = copy(size);
        newQ.size = size;
        newQ.begin = 0;
        return newQ;
    }

    public void clear(){
        elements = new Object[BASE_CAPACITY];
        begin = 0;
        size = 0;
    }
}
