package bluetix.service;

import java.util.LinkedList;

import org.springframework.stereotype.Service;

@Service
public class QueuingService<T> {
    private final LinkedList<T> queue = new LinkedList<>();

    //TODO: change enqueue to implement priority
    public void enqueue(T item) {
        queue.addLast(item);
    }

    public T dequeue() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int getPosition(T item){
        return queue.indexOf(item);
    }
}
