package bluetix.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class Resource {
    @PreAuthorize("@queuingSecurityService.isTurnInQueue(authentication,request)")
    @GetMapping("/secured/resource")
    public String securedResource() {
        // This method can only be accessed if it's the user's turn in the queue.
        // Implement your logic here.
        return "secured_resource";
    }

}
