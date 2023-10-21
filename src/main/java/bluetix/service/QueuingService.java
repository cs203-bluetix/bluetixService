package bluetix.service;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

@Service
public class QueuingService<T extends Comparable<T>> {

    // T should be a comparable, to add a priority score to users in a separate
    // class?

    private final PriorityQueue<T> queue = new PriorityQueue<>();
    private final List<T> service = new ArrayList<>();

    public QueuingService() {
        // Schedule the draining task to run every 5 seconds (adjust as needed)
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::drainQueue, 0, 5, TimeUnit.SECONDS);
    }

    // TODO: change enqueue to implement priority
    public void enqueue(T item) {
        if (!inQueueOrService(item))
            queue.offer(item);
        else{
            System.out.println("tf??");
            throw new RuntimeException();
        }
    }

    public boolean inQueueOrService(T item) {
        return (queue.contains(item) || service.contains(item));
    }

    public boolean inService(T item){
        return service.contains(item);
    }

    public T dequeue() {
        return this.queue.poll();
    }

    public void moveToService() {
        this.service.add(this.dequeue());
    }

    public void drainQueue() {
        while (this.service.size() < 50 && !this.queue.isEmpty()) {
            moveToService();
        }
    }

    public void removeFromService(T object) {
        service.remove(object);
    }

    public boolean isEmpty() {
        return queue.isEmpty() && service.isEmpty();
    }

    public int getPosition(T item) {
        // return queue.(item);
        return 1;
    }
}
