package queue;

import java.util.Arrays;

public class ArrayQueue{
    private final int BASE_CAPACITY = 3;
    private int begin;
    private int size;
    private Object[] elements = new Object[BASE_CAPACITY];

    //-----╔══╦╗─╔╦╗╔╦══╦═══╦══╦══╦╗─╔╦════╗
    //-----╚╗╔╣╚═╝║║║║╔╗║╔═╗╠╗╔╣╔╗║╚═╝╠═╗╔═╝
    //-----─║║║╔╗─║║║║╚╝║╚═╝║║║║╚╝║╔╗─║─║║
    //-----─║║║║╚╗║╚╝║╔╗║╔╗╔╝║║║╔╗║║╚╗║─║║
    //-----╔╝╚╣║─║╠╗╔╣║║║║║║╔╝╚╣║║║║─║║─║║
    //-----╚══╩╝─╚╝╚╝╚╝╚╩╝╚╝╚══╩╝╚╩╝─╚╝─╚╝
    // putted item will be taken after $size dequeues
    // putted item will be taken right now upon remove or peek
    // putted in head item will be taken right now upon dequeue
    // putted in head item will be taken after $size dequeue
    // etc

    //element != null
    public void enqueue(Object element) {
        assert element != null;
        if (size == capacity()) {
            ensureCapacity(capacity() * 2);
        }
        elements[end()] = element;
        size++;
    }
    // size' - size = 1 && after n of dequeue u get $element && pop() return $element

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

    // deque is not empty
    public Object dequeue() {
        Object value = element();
        elements[begin] = null;
        begin = (begin+1)%capacity();
        size--;
        return value;
    }
    // returned element[begin] element && $element was putted $old_size dequeues ago
    // size' - size = -1

    // element != null
    public void push(Object element){
        assert element != null;
        if (size == capacity()) {
            ensureCapacity(capacity() * 2);
        }
        begin = (begin -1 + capacity())%capacity();
        elements[begin] = element;
        size++;
    }
    //

    // deque is not empty
    public Object peek(){
        assert !isEmpty();
        return elements[(begin+size-1)%capacity()];
    }
    // upon dequeue, the $element could be removed after $size times
    // $element is last in queue

    // dequeueueueueueueueueueueueu is not empty
    public Object remove() {
        Object value = peek();
        size--;
        elements[(begin+size)%capacity()] = null;
        return value;
    }
    // last element in queueueueue == null && size' = size - 1
    // returned last in queueueue

    // dequeueueueueueueueueueueueu is not empty
    public Object element() {
        assert size > 0;
        return elements[begin];
    }
    // returned last in queueueue

    // any
    public int size() {
        return size;
    }
    // return number of elements in deque

    // empty || not empty
    public boolean isEmpty() {
        return size == 0;
    }
    // empty || not empty, empty == 0 elements in dequeueue

    // any
    public ArrayQueue makeCopy() {
        final ArrayQueue newQ = new ArrayQueue();
        newQ.elements = copy(size);
        newQ.size = size;
        newQ.begin = 0;
        return newQ;
    }
    // newQ.size = this, but newQ.elements = [begin..end, null...null) && begin = 0;

    // any
    public void clear(){
        elements = new Object[BASE_CAPACITY];
        begin = 0;
        size = 0;
    }
    // virginal clean
}
