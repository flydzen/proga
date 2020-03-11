package queue;


import java.util.function.Function;
import java.util.function.Predicate;

public class LinkedQueue extends AbstractQueue {
    private static Node cou;
    private class Node{
        private Object value;
        private Node prev;
        private Node next;
        Node(Object value, Node next, Node prev){
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
        Node(Object value){
            this.value = value;
            this.prev = null;
            this.next = null;
        }

        public Node getPrev() {
            return prev;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    private Node head;
    private Node tail;

    protected void enqueue_(Object element) {
        Node el = new Node(element, tail, null);
        if (tail != null) {
            tail.setPrev(el);
        }
        tail = el;
        if (head == null){
            head = el;
        }
    }

    @Override
    public void dequeue_() {
        head = head.getPrev();
        if (size == 0){
            tail = null;
        }else{
            head.setNext(null);
        }
    }

    @Override
    public Object element_() {
        return head.value;
    }

    public void push_(Object element){
        Node el = new Node(element, null, head);
        if (head != null) {
            head.setNext(el);
        }
        head = el;
        if (tail == null){
            tail = el;
        }
    }

    public Object peek_(){
        return tail.value;
    }

    public void remove_() {
        size--;
        tail.getNext().setPrev(null);
    }

    @Override
    public Queue makeCopy() {
        LinkedQueue q = new LinkedQueue();
        q.head = new Node(head.value);
        Node re = head.getPrev();
        Node last = q.head;
        while (re != tail){
            Node newN = new Node(re.value);
            newN.setNext(last);
            last.setPrev(newN);
            re = re.getPrev();
        }
        q.tail = last;
        return q;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    public void reset(){
        cou = head;
    }

    public Object getNext(){
        Object data = cou.value;
        cou = cou.getPrev();
        return data;
    }

    public Queue getQueue(){
        return new LinkedQueue();
    }
}
