package bluetix.controller;

import bluetix.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;

import bluetix.model.*;
import bluetix.repository.EventRepo;
import bluetix.serializable.SessionId;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/queue")

public class QueueController {
    @Autowired
    private QueueManagementService queueService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private EventRepo eventRepo;

    @PostMapping("/join/{eventId}/{sessionId}")
    public ResponseEntity<String> joinQueue(@PathVariable("eventId") Long eventId,
            @PathVariable("sessionId") Long sessionId, @AuthenticationPrincipal User user) {
        try {
            queueService.initializeQueueForSession(eventId, sessionId);
            queueService.addUserToQueue(eventId, sessionId, user);
            return ResponseEntity.ok(user.getEmail() + " Joined the queue.");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @DeleteMapping("leaveQueue/{eventId}/{sessionId}")
    public ResponseEntity<String> leaveQueue(@PathVariable("eventId") Long eventId,
            @PathVariable("sessionId") Long sessionId, @AuthenticationPrincipal User user) {
        try {
            queueService.initializeQueueForSession(eventId, sessionId, user);
            return ResponseEntity.ok(user.getEmail() + " Left the queue.");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return ResponseEntity.unprocessableEntity().build();
        }
    }
    // @GetMapping("/position")
    // public ResponseEntity<Integer> getPositionInQueue(@AuthenticationPrincipal
    // User user) {
    // int position = queueService.getPosition(user);
    // return ResponseEntity.ok(position);
    // }
}
