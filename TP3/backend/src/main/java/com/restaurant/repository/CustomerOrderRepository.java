package com.restaurant.repository;

import com.restaurant.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import java.util.List;
import java.util.Optional;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    @Override
    @EntityGraph(attributePaths = {"items", "items.menuItem", "assignedEmployee"})
    List<CustomerOrder> findAll();

    @Override
    @EntityGraph(attributePaths = {"items", "items.menuItem", "assignedEmployee"})
    Optional<CustomerOrder> findById(Long id);
}
