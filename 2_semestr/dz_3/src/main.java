import queue.ArrayQueue;

public class main {
    public static void main(String[] args){
        ArrayQueue arr = new ArrayQueue();
        arr.enqueue(3);
        arr.enqueue(3);
        arr.enqueue(new String[]{"a"});
        arr.enqueue("world");
        System.out.println(arr.element());
        System.out.println(arr.dequeue());
        System.out.println(arr.dequeue());
        System.out.println(arr.dequeue());
        System.out.println(arr.element());
    }
}
