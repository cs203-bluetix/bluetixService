package bluetix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bluetix.exception.DataNotFoundException;
import bluetix.model.TicketCategory;
import bluetix.repository.TicketCategoryRepo;

@RestController
@RequestMapping("/api/ticketcategories")
public class TicketCategoryController {

    @Autowired
    private TicketCategoryRepo ticketRepo;

    @PostMapping
    TicketCategory createTicketCategory(@RequestBody TicketCategory newTicketCategory) {
        return ticketRepo.save(newTicketCategory);
    }

    @GetMapping
    List<TicketCategory> getAllTicketCategories() {
        return ticketRepo.findAll();
    }

    @GetMapping("/{id}")
    TicketCategory getTicketCategoryById(@PathVariable Long id) {
        return ticketRepo.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Ticket category not found"));
    }

    @PutMapping("/{id}")
    TicketCategory updateTicketCategory(@PathVariable Long id, @RequestBody TicketCategory updatedTicketCategory) {
        TicketCategory ticketCategory = ticketRepo.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Ticket category not found"));

        // Update the properties of the ticket category with the values from updatedTicketCategory
        // Example: ticketCategory.setName(updatedTicketCategory.getName());
        //Dont really need

        return ticketRepo.save(ticketCategory);
    }

    @DeleteMapping("/{id}")
    void deleteTicketCategory(@PathVariable Long id) {
        if (ticketRepo.existsById(id)) {
            ticketRepo.deleteById(id);
        } else {
            throw new DataNotFoundException("Ticket category not found");
        }
    }
}