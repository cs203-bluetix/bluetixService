package bluetix.service;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class QueuingService<T extends Comparable<T>> {

    // T should be a comparable, to add a priority score to users in a separate
    // class?

    private final PriorityQueue<T> queue = new PriorityQueue<>();
    private final Map<T, Long> service = new HashMap<>();

    public QueuingService() {
        // Schedule the draining task to run every 5 seconds (adjust as needed)
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::drainQueue, 0, 5, TimeUnit.SECONDS);
    }

    // TODO: change enqueue to implement priority
    public void enqueue(T item) {
        if (!inQueueOrService(item))
            queue.offer(item);
        else {
            System.out.println("tf??");
            throw new RuntimeException();
        }
    }

    public boolean inQueueOrService(T item) {
        return (queue.contains(item) || service.containsKey(item));
    }

    public boolean inService(T item) {
        return service.containsKey(item);
    }

    public T dequeue() {
        return this.queue.poll();
    }

    public void moveToService() {
        T item = this.dequeue();
        service.put(item, System.currentTimeMillis());
    }

    public void drainQueue() {
        long currentTime = System.currentTimeMillis();
        Iterator<Map.Entry<T, Long>> iterator = service.entrySet().iterator();
        while (this.service.size() < 50 && !this.queue.isEmpty()) {
            moveToService();
        }
        while(iterator.hasNext()){
            Map.Entry<T, Long> entry = iterator.next();
            T item = entry.getKey();
            long timestamp = entry.getValue();

            if (currentTime - timestamp > TimeUnit.MINUTES.toMillis(5)) {
                // Remove items that have been in service for more than 5 minutes
                iterator.remove();
            }
        }
    }

    public void removeFromService(T object) {
        if (service.containsKey(object))
            service.remove(object);
        else
            throw new RuntimeException();
    }

    public boolean isEmpty() {
        return queue.isEmpty() && service.isEmpty();
    }

    public int getPosition(T item) {
        // return queue.(item);
        return 1;
    }
}
