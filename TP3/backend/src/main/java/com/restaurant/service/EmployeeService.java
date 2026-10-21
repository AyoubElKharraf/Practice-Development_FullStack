package com.restaurant.service;

import com.restaurant.model.Employee;
import com.restaurant.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository repository;
    public EmployeeService(EmployeeRepository repository) { this.repository = repository; }
    public List<Employee> findAll() { return repository.findAll(); }
    public Employee findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee", id)); }
    public Employee save(Employee employee) { return repository.save(employee); }
    public Employee update(Long id, Employee input) {
        Employee current = findById(id);
        current.setFirstName(input.getFirstName()); current.setLastName(input.getLastName());
        current.setRole(input.getRole()); current.setEmail(input.getEmail()); current.setPhone(input.getPhone());
        return repository.save(current);
    }
    public void delete(Long id) { repository.delete(findById(id)); }
}
