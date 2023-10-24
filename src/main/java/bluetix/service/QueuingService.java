package bluetix.service;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import bluetix.model.User;
import bluetix.repository.UserRepo;

public class QueuingService {

    // T should be a comparable, to add a priority score to users in a separate
    // class?

    private final PriorityQueue<User> queue = new PriorityQueue<>();
    private final Map<User, Long> service = new HashMap<>();

    private int ticketCount;
    private final UserRepo userRepo;

    @Autowired
    public QueuingService(int ticketCount, UserRepo userRepo) {
        // Schedule the draining task to run every 5 seconds (adjust as needed)
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::drainQueue, 0, 5, TimeUnit.SECONDS);
        this.ticketCount = ticketCount;
        this.userRepo = userRepo;
    }

    // TODO: change enqueue to implement priority
    public void enqueue(User item) {
        if (!inQueueOrService(item))
            queue.offer(item);
        else {
            System.out.println("tf??");
            throw new RuntimeException();
        }
    }

    public boolean inQueueOrService(User item) {
        return (queue.contains(item) || service.containsKey(item));
    }

    public boolean inService(User item) {
        return service.containsKey(item);
    }

    public User dequeue() {
        return this.queue.poll();
    }

    public void moveToService() {
        User item = this.dequeue();
        service.put(item, System.currentTimeMillis());
    }

    public void drainQueue() {
        long currentTime = System.currentTimeMillis();
        Iterator<Map.Entry<User, Long>> iterator = service.entrySet().iterator();
        while (this.service.size() < ticketCount && !this.queue.isEmpty()) {
            moveToService();
        }
        while(iterator.hasNext()){
            Map.Entry<User, Long> entry = iterator.next();
            User item = entry.getKey();
            long timestamp = entry.getValue();

            if (currentTime - timestamp > TimeUnit.MINUTES.toMillis(5)) {
                // Remove items that have been in service for more than 5 minutes
                iterator.remove();
            }
        }
    }

    public void removeFromService(User object) {
        if (service.containsKey(object))
            service.remove(object);
        else
            throw new RuntimeException();
    }

    public boolean isEmpty() {
        return queue.isEmpty() && service.isEmpty();
    }

    public int getPosition(User item) {
        // return queue.(item);
        return 1;
    }
}
