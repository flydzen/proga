package queue;

import java.util.function.Function;
import java.util.function.Predicate;

public abstract class AbstractQueue implements Queue {
    protected int size;
    protected abstract void enqueue_(Object element);
    protected abstract void dequeue_();
    protected abstract Object element_();
    protected abstract void push_(Object element);
    protected abstract void remove_();
    protected abstract Object peek_();

    public void enqueue(Object element) {
        assert element != null;
        enqueue_(element);
        size++;
    }

    @Override
    public Object dequeue() {
        assert size > 0;
        Object el = element();
        size--;
        dequeue_();
        return el;
    }

    @Override
    public Object element() {
        assert size > 0;
        return element_();
    }
    public void push(Object element){
        assert element != null;
        push_(element);
        size++;
    }

    public Object peek(){
        assert !isEmpty();
        return peek_();
    }

    public Object remove() {
        Object value = peek();
        remove_();
        size--;
        return value;
    }

    public boolean isEmpty() {
        return size == 0;
    }


    // any
    public int size() {
        return size;
    }
    // return number of elements in deque

    public Queue filter(Predicate<Object> p){
        Queue q = getQueue();
        reset();
        for (int i = 0; i < size; i++) {
            Object data = getNext();
            if (p.test(data)){
                q.enqueue(data);
            }
        }
        return q;
    }

    public Queue map(Function<Object, Object> f){
        Queue q = getQueue();
        reset();
        for (int i = 0; i < size; i++) {
            q.enqueue(f.apply(getNext()));
        }
        return q;
    }
}
