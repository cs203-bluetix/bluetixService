package bluetix.service;

import java.util.*;

import org.springframework.stereotype.Service;

@Service
public class QueuingService<T> {

    // T should be a comparable, to add a priority score to users in a separate class?

    private final PriorityQueue<T> queue = new PriorityQueue<>();
    private final List<T> inService = new ArrayList<>();

    // TODO: change enqueue to implement priority
    public void enqueue(T item) {
        if (!queue.contains(item) && !inService.contains(item))
            queue.offer(item);
    }

    public T dequeue() {
        return queue.poll();
    }

    public void moveToService() {
        inService.add(queue.poll());
    }

    public void removeFromService(T object) {
        inService.remove(object);
    }

    public boolean isEmpty() {
        return queue.isEmpty() && inService.isEmpty();
    }

    public int getPosition(T item) {
        // return queue.(item);
        return 1;
    }
}
