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
        scheduler.scheduleAtFixedRate(this::drainQueue, 0, 10, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(this::kickIdleUsers, 0, 5, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(this::kickFromQueue, 0, 5, TimeUnit.SECONDS);
        this.ticketCount = ticketCount;
        this.userRepo = userRepo;
    }

    PriorityQueue<User> getQueue() {
        return queue;
    }

    Map<User, Long> getService() {
        return service;
    }

    // TODO: change enqueue to implement priority
    public void enqueue(User user) {
        if (!inQueueOrService(user)) {
            user.setTimeStamp(System.currentTimeMillis());
            if (user.getFailedPurchases() == null) {
                user.setFailedPurchases(0);
            }
            this.userRepo.save(user);
            queue.offer(user);
        } else {
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
        this.service.put(item, System.currentTimeMillis());
    }

    public void drainQueue() {
        while (this.service.size() < ticketCount && !this.queue.isEmpty()) {
            moveToService();
        }
    }

    public void kickIdleUsers() {
        long currentTime = System.currentTimeMillis();
        Iterator<Map.Entry<User, Long>> iterator = this.service.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<User, Long> entry = iterator.next();
            User item = entry.getKey();
            long timestamp = entry.getValue();

            if (currentTime - timestamp > TimeUnit.MINUTES.toMillis(5)) {
                // Remove items that have been in service for more than 5 minutes
                iterator.remove();
            }
        }
    }

    // called when no more tickets are left
    public void kickFromQueue() {
        if (ticketCount == 0) {
            Iterator<User> queueIterator = queue.iterator();
            while (queueIterator.hasNext()) {
                User user = queueIterator.next();
                user.setFailedPurchases(user.getFailedPurchases() + 1);
                this.userRepo.save(user);
                queueIterator.remove();
            }
        }
    }

    public void removeFromService(User user) {
        if (this.service.containsKey(user)) {
            this.service.remove(user);
            user.setFailedPurchases(0);
            this.userRepo.save(user);
            this.ticketCount -= 1;
        } else
            throw new RuntimeException();
    }

    public boolean isEmpty() {
        return queue.isEmpty() && service.isEmpty();
    }

}
