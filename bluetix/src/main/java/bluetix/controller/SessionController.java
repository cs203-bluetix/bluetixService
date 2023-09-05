package bluetix.controller;

import bluetix.exception.DataNotFoundException;
import bluetix.model.Session;
import bluetix.repository.SessionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/sessions")
public class SessionController {

	@Autowired
    private SessionRepo sessionRepository;

    @GetMapping
    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    @GetMapping("/{sessionId}")
    public Session getSessionById(@PathVariable Long sessionId) {
        return sessionRepository.findById(sessionId)
                .orElseThrow(() -> new DataNotFoundException("Session not found"));
    }

    @PostMapping
    public Session createSession(@RequestBody Session session) {
        return sessionRepository.save(session);
    }

    @PutMapping("/{sessionId}")
    public Session updateSession(@PathVariable Long sessionId, @RequestBody Session sessionDetails) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new DataNotFoundException("Session not found"));
        //dont really need

        return sessionRepository.save(session);
    }

    @DeleteMapping("/{sessionId}")
    public void deleteSession(@PathVariable Long sessionId) {
        sessionRepository.deleteById(sessionId);
    }
}