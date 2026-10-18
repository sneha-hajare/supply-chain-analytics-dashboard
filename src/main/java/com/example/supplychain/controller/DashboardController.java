
package com.example.supplychain.controller;

import com.example.supplychain.service.SupplierService;
import com.example.supplychain.service.InventoryService;
import com.example.supplychain.service.SupplyOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final SupplierService supplierService;
    private final InventoryService inventoryService;
    private final SupplyOrderService orderService;

    public DashboardController(
            SupplierService supplierService,
            InventoryService inventoryService,
            SupplyOrderService orderService) {
        this.supplierService = supplierService;
        this.inventoryService = inventoryService;
        this.orderService = orderService;
    }

    @GetMapping("/")
    public String dashboard(Model model) {

        var suppliers = supplierService.getAllSuppliers();
        var inventory = inventoryService.getAllInventory();
        var orders = orderService.getAllOrders();

        model.addAttribute("totalSuppliers", suppliers.size());

        model.addAttribute("averageRating",
                suppliers.stream()
                        .mapToDouble(s -> s.getRating())
                        .average()
                        .orElse(0.0));

        model.addAttribute("totalProducts", inventory.size());

        model.addAttribute("lowStockCount",
                inventoryService.getLowStockCount());

        model.addAttribute("totalOrders", orders.size());

        model.addAttribute("pendingOrders",
                orders.stream()
                        .filter(o -> "Pending".equalsIgnoreCase(o.getStatus()))
                        .count());

        model.addAttribute("totalOrderValue",
                orders.stream()
                        .filter(o -> !"Cancelled".equalsIgnoreCase(o.getStatus()))
                        .mapToDouble(o -> o.getTotalCost())
                        .sum());

        return "dashboard";
    }
}
