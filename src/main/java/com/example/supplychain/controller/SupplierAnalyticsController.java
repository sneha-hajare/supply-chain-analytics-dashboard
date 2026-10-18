
package com.example.supplychain.controller;

import com.example.supplychain.service.SupplierService;
import com.example.supplychain.entity.Supplier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.List;

@Controller
public class SupplierAnalyticsController {

    private final SupplierService supplierService;

    public SupplierAnalyticsController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/supplier-analytics")
    public String showSupplierAnalytics(Model model) {

        List<Supplier> suppliers = supplierService.getAllSuppliers();

        double averageRating = suppliers.stream()
                .mapToDouble(Supplier::getRating)
                .average()
                .orElse(0.0);

        Supplier topSupplier = suppliers.stream()
                .max(Comparator.comparingDouble(Supplier::getRating))
                .orElse(null);

        model.addAttribute("suppliers", suppliers);
        model.addAttribute("totalSuppliers", suppliers.size());
        model.addAttribute("averageRating", averageRating);
        model.addAttribute("topSupplier", topSupplier);

        return "supplier-analytics";
    }
}
