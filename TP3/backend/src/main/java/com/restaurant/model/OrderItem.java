package com.restaurant.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonBackReference
    private CustomerOrder order;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "menu_item_id", nullable = false)
    private MenuItem menuItem;
    @Min(1) @Column(nullable = false)
    private int quantity;

    public Long getId() { return id; }
    public CustomerOrder getOrder() { return order; }
    public void setOrder(CustomerOrder value) { order = value; }
    public MenuItem getMenuItem() { return menuItem; }
    public void setMenuItem(MenuItem value) { menuItem = value; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int value) { quantity = value; }
}
