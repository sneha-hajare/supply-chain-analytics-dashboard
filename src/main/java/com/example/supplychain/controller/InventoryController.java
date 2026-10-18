
package com.example.supplychain.controller;

import com.example.supplychain.entity.Inventory;
import com.example.supplychain.service.InventoryService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public String listInventory(Model model) {
        model.addAttribute("inventory",
                inventoryService.getAllInventory());
        model.addAttribute("lowStockCount",
                inventoryService.getLowStockCount());
        return "inventory";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("item", new Inventory());
        return "inventory-form";
    }

    @PostMapping("/save")
    public String saveInventory(
            @ModelAttribute("item") Inventory item) {

        inventoryService.saveInventory(item);
        return "redirect:/inventory";
    }

    // Open the edit form for an existing product
    @GetMapping("/edit/{id}")
    public String editInventory(
            @PathVariable Long id,
            Model model) {

        Optional<Inventory> item =
                inventoryService.getInventoryById(id);

        if (item.isEmpty()) {
            return "redirect:/inventory";
        }

        model.addAttribute("item", item.get());
        return "inventory-form";
    }

    // Update an existing product
    @PostMapping("/update/{id}")
    public String updateInventory(
            @PathVariable Long id,
            @ModelAttribute("item") Inventory updatedItem) {

        Optional<Inventory> existingItem =
                inventoryService.getInventoryById(id);

        if (existingItem.isEmpty()) {
            return "redirect:/inventory";
        }

        Inventory item = existingItem.get();

        item.setProductName(updatedItem.getProductName());
        item.setCategory(updatedItem.getCategory());
        item.setQuantity(updatedItem.getQuantity());
        item.setReorderLevel(updatedItem.getReorderLevel());
        item.setUnitPrice(updatedItem.getUnitPrice());

        inventoryService.saveInventory(item);

        return "redirect:/inventory";
    }

    @PostMapping("/delete/{id}")
    public String deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
        return "redirect:/inventory";
    }
}
