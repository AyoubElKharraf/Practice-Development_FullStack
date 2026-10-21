package com.restaurant.web;

import com.restaurant.model.MenuItem;
import com.restaurant.service.MenuItemService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/menu-items") @CrossOrigin(origins = "http://localhost:4200")
public class MenuItemController {
    private final MenuItemService service;
    public MenuItemController(MenuItemService service) { this.service = service; }
    @GetMapping public List<MenuItem> all() { return service.findAll(); }
    @GetMapping("/{id}") public MenuItem one(@PathVariable Long id) { return service.findById(id); }
    @PostMapping public ResponseEntity<MenuItem> create(@Valid @RequestBody MenuItem item) { return ResponseEntity.status(HttpStatus.CREATED).body(service.save(item)); }
    @PutMapping("/{id}") public MenuItem update(@PathVariable Long id, @Valid @RequestBody MenuItem item) { return service.update(id, item); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { service.delete(id); }
}
