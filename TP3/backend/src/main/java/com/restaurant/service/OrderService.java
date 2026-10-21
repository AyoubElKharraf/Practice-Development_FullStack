package com.restaurant.service;

import com.restaurant.dto.OrderItemRequest;
import com.restaurant.dto.OrderRequest;
import com.restaurant.model.*;
import com.restaurant.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class OrderService {
    private final CustomerOrderRepository orderRepository;
    private final EmployeeRepository employeeRepository;
    private final MenuItemRepository menuRepository;

    public OrderService(CustomerOrderRepository orderRepository, EmployeeRepository employeeRepository,
                        MenuItemRepository menuRepository) {
        this.orderRepository = orderRepository; this.employeeRepository = employeeRepository; this.menuRepository = menuRepository;
    }
    public List<CustomerOrder> findAll() { return orderRepository.findAll(); }
    public CustomerOrder findById(Long id) { return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order", id)); }
    @Transactional
    public CustomerOrder create(OrderRequest request) { return orderRepository.save(toEntity(new CustomerOrder(), request)); }
    @Transactional
    public CustomerOrder update(Long id, OrderRequest request) {
        CustomerOrder order = findById(id);
        order.getItems().clear();
        return orderRepository.save(toEntity(order, request));
    }
    public void delete(Long id) { orderRepository.delete(findById(id)); }

    private CustomerOrder toEntity(CustomerOrder order, OrderRequest request) {
        if (request.orderDate() != null) order.setOrderDate(request.orderDate());
        if (request.status() != null) order.setStatus(request.status());
        order.setAssignedEmployee(request.employeeId() == null ? null :
                employeeRepository.findById(request.employeeId())
                        .orElseThrow(() -> new ResourceNotFoundException("Employee", request.employeeId())));
        for (OrderItemRequest itemRequest : request.items()) {
            MenuItem menuItem = menuRepository.findById(itemRequest.menuItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Menu item", itemRequest.menuItemId()));
            OrderItem item = new OrderItem();
            item.setMenuItem(menuItem); item.setQuantity(itemRequest.quantity()); order.addItem(item);
        }
        return order;
    }
}
