package bluetix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import bluetix.model.User;
import bluetix.repository.UserRepo;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/user")
//TODO: add cryptowallet setter
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/createUser")
    User createUser(@RequestBody User newUser) {
        return userRepo.save(newUser);
    }

    @GetMapping
    List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @GetMapping("/{id}")
    User getUserById(@PathVariable Long id) {
        return userRepo.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User user = userRepo.findById(id).orElse(null);
        if (user != null) {
            // Update the properties of the user with the values from updatedUser
            user.setEmail(updatedUser.getEmail());
            //user.setName(updatedUser.getName());
            // Update any other properties here

            return userRepo.save(user);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable Long id) {
        if (userRepo.existsById(id)) {
            userRepo.deleteById(id);
        }
    }
}
