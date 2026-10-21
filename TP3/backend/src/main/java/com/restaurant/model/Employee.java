package com.restaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employees")
public class Employee {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank @Size(max = 80) private String firstName;
    @NotBlank @Size(max = 80) private String lastName;
    @NotBlank @Size(max = 60) private String role;
    @NotBlank @Email @Size(max = 150) @Column(unique = true) private String email;
    @NotBlank @Size(max = 30) private String phone;
    @OneToMany(mappedBy = "assignedEmployee")
    @JsonIgnore
    private List<CustomerOrder> orders = new ArrayList<>();

    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String value) { firstName = value; }
    public String getLastName() { return lastName; }
    public void setLastName(String value) { lastName = value; }
    public String getRole() { return role; }
    public void setRole(String value) { role = value; }
    public String getEmail() { return email; }
    public void setEmail(String value) { email = value; }
    public String getPhone() { return phone; }
    public void setPhone(String value) { phone = value; }
    public List<CustomerOrder> getOrders() { return orders; }
}
