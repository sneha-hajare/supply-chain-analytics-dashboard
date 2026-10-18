
package com.example.supplychain.controller;

import com.example.supplychain.entity.SupplyOrder;
import com.example.supplychain.service.SupplyOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class SupplyOrderController {

    private final SupplyOrderService orderService;

    public SupplyOrderController(SupplyOrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "orders";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("order", new SupplyOrder());
        model.addAttribute("editMode", false);
        return "order-form";
    }

    @PostMapping("/save")
    public String saveOrder(@ModelAttribute("order") SupplyOrder order) {
        orderService.saveOrder(order);
        return "redirect:/orders";
    }

    @GetMapping("/edit/{id}")
    public String editOrder(@PathVariable Long id, Model model) {
        SupplyOrder order = orderService.getOrderById(id);

        if (order == null) {
            return "redirect:/orders";
        }

        model.addAttribute("order", order);
        model.addAttribute("editMode", true);
        return "order-form";
    }

    @PostMapping("/update/{id}")
    public String updateOrder(
            @PathVariable Long id,
            @ModelAttribute("order") SupplyOrder updatedOrder) {

        SupplyOrder existingOrder = orderService.getOrderById(id);

        if (existingOrder == null) {
            return "redirect:/orders";
        }

        existingOrder.setProductName(updatedOrder.getProductName());
        existingOrder.setQuantity(updatedOrder.getQuantity());
        existingOrder.setTotalCost(updatedOrder.getTotalCost());
        existingOrder.setSupplierName(updatedOrder.getSupplierName());
        existingOrder.setOrderDate(updatedOrder.getOrderDate());
        existingOrder.setStatus(updatedOrder.getStatus());

        orderService.saveOrder(existingOrder);

        return "redirect:/orders";
    }

    @PostMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }
}
