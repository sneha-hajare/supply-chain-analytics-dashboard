
package com.example.supplychain.controller;

import com.example.supplychain.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@PreAuthorize("hasRole('ADMIN')")
public class UserManagementController {

    private final UserService userService;

    public UserManagementController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/users/new")
    public String showCreateUserForm() {
        return "user-form";
    }

    @PostMapping("/admin/users/create")
    public String createUser(
            @RequestParam String fullName,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String role,
            RedirectAttributes redirectAttributes) {

        try {
            userService.createUser(
                    fullName, username, password, role);

            redirectAttributes.addFlashAttribute(
                    "successMessage", "User created successfully.");

            return "redirect:/admin/users/new";

        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage", ex.getMessage());

            return "redirect:/admin/users/new";
        }
    }
}
