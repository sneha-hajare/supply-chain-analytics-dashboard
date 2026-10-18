
package com.example.supplychain.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "supply_orders")
public class SupplyOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String productName;

    private int quantity;

    private double totalCost;

    private String supplierName;

    private LocalDate orderDate;

    private String status = "Pending";

    public SupplyOrder() {
    }

    @PrePersist
    public void setDefaultOrderDate() {
        if (orderDate == null) {
            orderDate = LocalDate.now();
        }
        if (status == null || status.isBlank()) {
            status = "Pending";
        }
    }

    public Long getId() { return id; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getTotalCost() { return totalCost; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }

    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }

    public LocalDate getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
