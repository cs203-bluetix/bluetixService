package bluetix.controller;

import bluetix.service.*;

import java.util.ArrayDeque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bluetix.model.*;

@RestController
@RequestMapping("/queue")

public class QueueController {
    @Autowired
    private QueuingService<User> queueService;

    @PostMapping("/join")
    public ResponseEntity<String> joinQueue(@AuthenticationPrincipal User user) {
        queueService.enqueue(user);
        return ResponseEntity.ok("Joined the queue.");
    }

    @GetMapping("/position")
    public ResponseEntity<Integer> getPositionInQueue(@AuthenticationPrincipal User user) {
        int position = queueService.getPosition(user);
        return ResponseEntity.ok(position);
    }
}
