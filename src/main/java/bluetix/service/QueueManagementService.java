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
    private HashMap<Session, QueuingService<User>> sessionToQueueMap;

    @Autowired
    public QueueManagementService(SessionService sessionService) {
        this.sessionService = sessionService;
        this.sessionToQueueMap = new HashMap<>();
    }

    public void initializeQueueForEvent(Long event_id) {
        List<Session> allSession = this.sessionService.findByEventId(event_id);
        for (Session s : allSession) {
            this.sessionToQueueMap.put(s, new QueuingService<>());
        }
    }

    public void initializeQueueForSession(SessionId sessionId) {
        Session session = this.sessionService.findById(sessionId);
        this.sessionToQueueMap.put(session, new QueuingService<>());
    }

    public void addUserToQueue(SessionId sessionId, User user){
        Session session = this.sessionService.findById(sessionId);
        QueuingService<User> queue = this.sessionToQueueMap.get(session);
        queue.enqueue(user);
    }

}
