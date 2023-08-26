package bluetix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bluetix.model.User;
import bluetix.repository.UserRepo;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserRepo userRepo;
	
	@PostMapping("/createUser")
	User createUser(@RequestBody User newUser) {
		return userRepo.save(newUser);
	}
	
	@GetMapping("/getAllUsers")
	List<User> getAllUsers(){
		return userRepo.findAll();
	}
}
