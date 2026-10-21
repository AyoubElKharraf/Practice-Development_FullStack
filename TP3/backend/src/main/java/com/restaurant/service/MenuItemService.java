package com.restaurant.service;

import com.restaurant.model.MenuItem;
import com.restaurant.repository.MenuItemRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MenuItemService {
    private final MenuItemRepository repository;
    public MenuItemService(MenuItemRepository repository) { this.repository = repository; }
    public List<MenuItem> findAll() { return repository.findAll(); }
    public MenuItem findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Menu item", id)); }
    public MenuItem save(MenuItem item) { return repository.save(item); }
    public MenuItem update(Long id, MenuItem input) {
        MenuItem current = findById(id);
        current.setName(input.getName()); current.setDescription(input.getDescription());
        current.setPrice(input.getPrice()); current.setCategory(input.getCategory());
        current.setAvailable(input.isAvailable());
        return repository.save(current);
    }
    public void delete(Long id) { repository.delete(findById(id)); }
}
