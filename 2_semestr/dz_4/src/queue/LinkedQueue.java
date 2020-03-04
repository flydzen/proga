package queue;


public class LinkedQueue extends AbstractQueue {
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

    @Override
    public void enqueue(Object element) {
        Node el = new Node(element, tail, null);
        if (tail != null) {
            tail.setPrev(el);
        }
        tail = el;
        if (head == null){
            head = el;
        }
        size++;
    }

    @Override
    public Object dequeue() {
        assert size > 0;
        Object el = element();
        head = head.getPrev();
        size--;
        if (size == 0){
            tail = null;
        }else{
            head.setNext(null);
        }
        return el;
    }

    @Override
    public Object element() {
        assert size > 0;
        return head.value;
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
}
