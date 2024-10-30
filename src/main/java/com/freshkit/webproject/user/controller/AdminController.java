package com.freshkit.webproject.user.controller;

import com.freshkit.webproject.user.dto.AdminDto;
import com.freshkit.webproject.user.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public String adminPage(Model model) {
        List<AdminDto> users = adminService.getAllUsers();
        model.addAttribute("users", users);
        return "admin";
    }

    @PostMapping("/user")
    @ResponseBody
    public ResponseEntity<Void> createOrUpdateUser(@ModelAttribute AdminDto user) {
        adminService.saveOrUpdateUser(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{memberId}")
    @ResponseBody
    public ResponseEntity<AdminDto> getUser(@PathVariable String memberId) {
        AdminDto user = adminService.getUserById(memberId);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/user/{memberId}")
    @ResponseBody
    public ResponseEntity<Void> deleteUser(@PathVariable String memberId) {
        adminService.deleteUser(memberId);
        return ResponseEntity.ok().build();
    }
}
