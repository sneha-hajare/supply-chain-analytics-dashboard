
package com.example.supplychain.controller;

import com.example.supplychain.entity.Supplier;
import com.example.supplychain.service.SupplierService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/suppliers")
    public String listSuppliers(Model model) {
        model.addAttribute(
                "suppliers",
                supplierService.getAllSuppliers()
        );
        return "suppliers";
    }

    @GetMapping("/suppliers/new")
    public String showSupplierForm(Model model) {
        model.addAttribute("supplier", new Supplier());
        return "supplier-form";
    }

    @PostMapping("/suppliers/save")
    public String saveSupplier(
            @ModelAttribute("supplier") Supplier supplier) {

        supplierService.saveSupplier(supplier);
        return "redirect:/suppliers";
    }

    // Open the edit form for an existing supplier
    @GetMapping("/suppliers/edit/{id}")
    public String editSupplier(
            @PathVariable Long id,
            Model model) {

        Optional<Supplier> supplier =
                supplierService.getSupplierById(id);

        if (supplier.isEmpty()) {
            return "redirect:/suppliers";
        }

        model.addAttribute("supplier", supplier.get());
        return "supplier-form";
    }

    // Save the edited supplier using the existing save method
    @PostMapping("/suppliers/update/{id}")
    public String updateSupplier(
            @PathVariable Long id,
            @ModelAttribute("supplier") Supplier updatedSupplier) {

        Optional<Supplier> existingSupplier =
                supplierService.getSupplierById(id);

        if (existingSupplier.isEmpty()) {
            return "redirect:/suppliers";
        }

        Supplier supplier = existingSupplier.get();

        supplier.setSupplierName(updatedSupplier.getSupplierName());
        supplier.setContactPerson(updatedSupplier.getContactPerson());
        supplier.setEmail(updatedSupplier.getEmail());
        supplier.setLocation(updatedSupplier.getLocation());
        supplier.setRating(updatedSupplier.getRating());

        supplierService.saveSupplier(supplier);

        return "redirect:/suppliers";
    }

    @GetMapping("/suppliers/delete/{id}")
    public String deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return "redirect:/suppliers";
    }
}
