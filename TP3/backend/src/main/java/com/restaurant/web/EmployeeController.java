package com.restaurant.web;

import com.restaurant.model.Employee;
import com.restaurant.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/employees") @CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {
    private final EmployeeService service;
    public EmployeeController(EmployeeService service) { this.service = service; }
    @GetMapping public List<Employee> all() { return service.findAll(); }
    @GetMapping("/{id}") public Employee one(@PathVariable Long id) { return service.findById(id); }
    @PostMapping public ResponseEntity<Employee> create(@Valid @RequestBody Employee employee) { return ResponseEntity.status(HttpStatus.CREATED).body(service.save(employee)); }
    @PutMapping("/{id}") public Employee update(@PathVariable Long id, @Valid @RequestBody Employee employee) { return service.update(id, employee); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { service.delete(id); }
}
