package queue;

public interface Queue {
    public void enqueue(Object element);
    public Object dequeue();
    public Object element();
    public boolean isEmpty();
    public Queue makeCopy();
    public void clear();
    public int size();
}
