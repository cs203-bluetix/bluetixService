package bluetix.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import bluetix.model.Session;
import bluetix.model.User;
import bluetix.repository.UserRepo;
import bluetix.serializable.SessionId;

@Service
public class QueueManagementService {

    private final SessionService sessionService;
    private final UserRepo userRepo;

    private final HashMap<Session, QueuingService> sessionToQueueMap = new HashMap<>();

    @Autowired
    public QueueManagementService(SessionService sessionService, UserRepo userRepo) {
        this.sessionService = sessionService;
        this.userRepo = userRepo;
    }

    public void initializeQueueForSession(Long eventId, Long sessionId) {
        Session session = this.sessionService.findById(eventId, sessionId);
        if (session != null) {
            if (this.sessionToQueueMap.get(session) == null) {

                this.sessionToQueueMap.put(session, new QueuingService(session.getTicket().size(), this.userRepo));
            }
        }
    }

    public void addUserToQueue(Long eventId, Long sessionId, User user) {
        Session session = this.sessionService.findById(eventId, sessionId);
        System.out.println(session.getSessionId());

        QueuingService queue = this.sessionToQueueMap.get(session);
        queue.enqueue(user);
    }

    public boolean checkUserInQueue(Long eventId, Long sessionId, User user) {
        Session session = this.sessionService.findById(eventId, sessionId);
        System.out.println(session.getSessionId());
        QueuingService queue = this.sessionToQueueMap.get(session);
        return queue.inQueueOrService(user);
    }

    public boolean checkUserInService(Long eventId, Long sessionId, User user) {
        Session session = this.sessionService.findById(eventId, sessionId);
        QueuingService queue = this.sessionToQueueMap.get(session);
        return queue.inService(user);
    }

    public void leaveQueue(Long eventId, Long sessionId, User user) {
        Session session = this.sessionService.findById(eventId, sessionId);
        QueuingService queue = this.sessionToQueueMap.get(session);
        queue.removeFromService(user);
    }

}
