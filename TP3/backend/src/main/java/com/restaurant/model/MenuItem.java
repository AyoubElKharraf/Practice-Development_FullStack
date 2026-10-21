package com.restaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menu_items")
public class MenuItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank @Size(max = 120)
    private String name;
    @Size(max = 500)
    private String description;
    @NotNull @DecimalMin("0.00")
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal price;
    @NotBlank @Size(max = 80)
    private String category;
    @Column(nullable = false)
    private boolean available = true;
    @OneToMany(mappedBy = "menuItem")
    @JsonIgnore
    private List<OrderItem> orderItems = new ArrayList<>();

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    public List<OrderItem> getOrderItems() { return orderItems; }
}
