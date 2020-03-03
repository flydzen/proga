package queue;

import java.util.Arrays;

public class ArrayQueueADT{
    private static final int BASE_CAPACITY = 3;
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
    // EVERYWHERE ArrayQueueADT q != null and defined like "deque"

    //element != null
    public static void enqueue(ArrayQueueADT q,Object element) {
        assert element != null;
        if (q.size == capacity(q)) {
            ensureCapacity(q,capacity(q) * 2);
        }
        q.elements[end(q)] = element;
        q.size++;
    }
    // size' - size = 1 && after n of dequeue u get $element && pop() return $element

    //capacity > size
    private static Object[] copy(ArrayQueueADT q, int capacity){
        Object[] temp = new Object[capacity];
        if (end(q) > q.begin){
            System.arraycopy(q.elements, q.begin, temp, 0, q.size);
        } else {
            System.arraycopy(q.elements, q.begin, temp, 0, capacity(q)-q.begin);
            System.arraycopy(q.elements, 0, temp, capacity(q)-q.begin, end(q));
        }
        return temp;
    }
    // temp = [begin..end, null...null), size(temp) = capacity

    // capacity > deque.length;
    private static void ensureCapacity(ArrayQueueADT q, int capacity) {
        if (capacity > capacity(q)) {
            q.elements = copy(q, capacity);
            q.begin = 0;
        }
    }
    // deque.length' > deque.length && elements = [begin..end, null...null)

    // any
    private static int capacity(ArrayQueueADT q){
        return q.elements.length;
    }
    // return current maxsize of deque

    // any
    private static int end(ArrayQueueADT q){
        return (q.begin + q.size)%capacity(q);
    }
    // end == begin && size=capacity || elements[end] = null
    // size == 0 || elements[(end-1 + capacity)%capacity] !=null

    // deque is not empty
    public static Object dequeue(ArrayQueueADT q) {
        assert q.size > 0;
        Object value = element(q);
        q.elements[q.begin] = null;
        q.begin = (q.begin+1)%capacity(q);
        q.size--;
        return value;
    }
    // returned element[begin] element && $element was putted $old_size dequeues ago
    // size' - size = -1

    // element != null
    public static void push(ArrayQueueADT q, Object element){
        assert element != null;
        if (q.size == capacity(q)) {
            ensureCapacity(q,capacity(q) * 2);
        }
        q.begin = (q.begin -1 + capacity(q))%capacity(q);
        q.elements[q.begin] = element;
        q.size++;
    }
    //

    // deque is not empty
    public static Object peek(ArrayQueueADT q){
        assert !isEmpty(q);
        return q.elements[(q.begin+q.size-1)%capacity(q)];
    }
    // upon dequeue, the $element could be removed after $size times
    // $element is last in queue

    // dequeueueueueueueueueueueueu is not empty
    public static Object remove(ArrayQueueADT q) {
        Object value = peek(q);
        q.size--;
        q.elements[(q.begin+q.size)%capacity(q)] = null;
        return value;
    }
    // last element in queueueueue == null && size' = size - 1
    // returned last in queueueue

    // dequeueueueueueueueueueueueu is not empty
    public static Object element(ArrayQueueADT q) {
        assert q.size > 0;
        return q.elements[q.begin];
    }
    // returned last in queueueue

    // any
    public static int size(ArrayQueueADT q) {
        return q.size;
    }
    // return number of elements in deque

    // empty || not empty
    public static boolean isEmpty(ArrayQueueADT q) {
        return q.size == 0;
    }
    // empty || not empty, empty == 0 elements in dequeueue

    // any
    public static void clear(ArrayQueueADT q){
        q.elements = new Object[BASE_CAPACITY];
        q.begin = 0;
        q.size = 0;
    }
    // virginal clean
}
