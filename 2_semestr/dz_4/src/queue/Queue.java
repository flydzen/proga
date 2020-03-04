package queue;

public interface Queue {
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
    public void enqueue(Object element);
    // size' - size = 1 && after n of dequeue u get $element && pop() return $element

    // deque is not empty
    public Object dequeue();
    // returned element[begin] element && $element was putted $old_size deque ago
    // size' - size = -1

    // deque is not empty
    public Object element();
    // returned last in queue

    // element != null
    public void push(Object element);
    // size' - size = 1 && dequeue() return $element && after n of pop() u get $element

    // deque is not empty
    public Object peek();
    // upon dequeue, the $element could be removed after $size times
    // $element is last in queue

    // dequeue is not empty
    public Object remove();
    // last element in queue == null && size' = size - 1
    // returned last in queue

    // empty || not empty
    public boolean isEmpty();
    // empty || not empty, empty == 0 elements in deque

    // any
    public Queue makeCopy();
    // new size = this, new elements = [begin..end..) && new begin = 0;

    // any
    public void clear();
    // virginal clean

    // any
    public int size();
    // return number of elements in deque

}
