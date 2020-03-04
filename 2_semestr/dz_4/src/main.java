import queue.ArrayQueue;
import queue.LinkedQueue;
import queue.Queue;

public class main {
    public static void main(String[] args){
        Queue arr = new ArrayQueue();
        arr.enqueue(3);
        arr.enqueue(3);
        arr.enqueue(new String[]{"a"});
        arr.enqueue("world");
        System.out.println(arr.element());
        System.out.println(arr.dequeue());
        System.out.println(arr.dequeue());
        System.out.println(arr.dequeue());
        System.out.println(arr.element());
        arr.enqueue(3);
        arr.enqueue(5);
        System.out.println(arr.dequeue());
        arr.enqueue(6);
        System.out.println(arr.dequeue());
        System.out.println(arr.dequeue());
        System.out.println(arr.dequeue());
        arr.size();
    }
}
