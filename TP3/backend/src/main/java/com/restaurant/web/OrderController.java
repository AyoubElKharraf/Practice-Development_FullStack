package com.restaurant.web;

import com.restaurant.dto.OrderRequest;
import com.restaurant.model.CustomerOrder;
import com.restaurant.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/orders") @CrossOrigin(origins = "http://localhost:4200")
public class OrderController {
    private final OrderService service;
    public OrderController(OrderService service) { this.service = service; }
    @GetMapping public List<CustomerOrder> all() { return service.findAll(); }
    @GetMapping("/{id}") public CustomerOrder one(@PathVariable Long id) { return service.findById(id); }
    @PostMapping public ResponseEntity<CustomerOrder> create(@Valid @RequestBody OrderRequest request) { return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request)); }
    @PutMapping("/{id}") public CustomerOrder update(@PathVariable Long id, @Valid @RequestBody OrderRequest request) { return service.update(id, request); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { service.delete(id); }
}
