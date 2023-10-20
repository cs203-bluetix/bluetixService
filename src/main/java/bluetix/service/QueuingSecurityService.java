package bluetix.service;

import bluetix.model.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Bean;

@Service

//Deprecate, not useful anymore
public class QueuingSecurityService {
    @Autowired
    private QueuingService<User> queueService;

    public boolean check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        User user = (User) authentication.get().getPrincipal();
        // int userPosition = queueService.getPosition(user);

        // Determine if it's the user's turn based on their position
        // Return true to allow access, false otherwise
        return true; // 0 means it's their turn
    }
}
