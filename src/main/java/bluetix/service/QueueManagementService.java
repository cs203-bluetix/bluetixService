package bluetix.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bluetix.model.Session;
import bluetix.model.User;
import bluetix.serializable.SessionId;

@Service
public class QueueManagementService {

    private final SessionService sessionService;
    private final HashMap<Session, QueuingService<User>> sessionToQueueMap = new HashMap<>();

    @Autowired
    public QueueManagementService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    public void initializeQueueForEvent(Long event_id) {
        List<Session> allSession = this.sessionService.findByEventId(event_id);
        for (Session s : allSession) {
            this.sessionToQueueMap.put(s, new QueuingService<>());
        }
    }

    public void initializeQueueForSession(Long eventId, Long sessionId) {
        Session session = this.sessionService.findById(eventId, sessionId);
        if (this.sessionToQueueMap.get(session) == null) {
            this.sessionToQueueMap.put(session, new QueuingService<>());
        }
    }

    public void addUserToQueue(Long eventId, Long sessionId, User user) {
        Session session = this.sessionService.findById(eventId, sessionId);
        QueuingService<User> queue = this.sessionToQueueMap.get(session);
        queue.enqueue(user);
    }

    public boolean checkUserInQueue(Long eventId, Long sessionId, User user) {
        Session session = this.sessionService.findById(eventId, sessionId);
        QueuingService<User> queue = this.sessionToQueueMap.get(session);
        return queue.inQueueOrService(user);
    }

    public void checkUserInService(Long eventId, Long sessionId, User user) {
        Session session = this.sessionService.findById(eventId, sessionId);
        QueuingService<User> queue = this.sessionToQueueMap.get(session);
        queue.inService(user);
    }

    public void leaveQueue(Long eventId, Long sessionId, User user) {
        Session session = this.sessionService.findById(eventId, sessionId);
        QueuingService<User> queue = this.sessionToQueueMap.get(session);
        queue.removeFromService(user);
    }

}
