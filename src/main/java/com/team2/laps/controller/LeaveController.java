package com.team2.laps.controller;

import javax.annotation.security.RolesAllowed;

import com.team2.laps.model.Leave;
import com.team2.laps.model.LeaveStatus;
import com.team2.laps.service.LeaveService;
import com.team2.laps.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {
    @Autowired
    LeaveService leaveService;

    @Autowired
    UserService userService;

    @GetMapping
    @RolesAllowed({ "ROLE_ADMINISTRATIVE_STAFF", "ROLE_PROFESSIONAL_STAFF", "ROLE_MANAGER" })
    public ResponseEntity<?> getLeaveByUser(Authentication authentication) {
        boolean isManager = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_MANAGER"));
        return ResponseEntity.ok(leaveService.getLeaveByUser(isManager));
    }

    @PostMapping
    @RolesAllowed({ "ROLE_ADMINISTRATIVE_STAFF", "ROLE_PROFESSIONAL_STAFF" })
    public ResponseEntity<?> createLeave(@RequestBody Leave leave, Authentication authentication) {
        leave.setUser(userService.getCurrentUser());
        return ResponseEntity.ok(leaveService.createLeave(leave));
    }

    @PutMapping
    @RolesAllowed({ "ROLE_ADMINISTRATIVE_STAFF", "ROLE_PROFESSIONAL_STAFF", "ROLE_MANAGER" })
    public ResponseEntity<?> updateLeave(@RequestBody Leave leave, Authentication authentication) {
        boolean isManager = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_MANAGER"));
        leave.setUser(userService.getCurrentUser());
        return ResponseEntity.ok(leaveService.updateLeave(leave, isManager));
    }

    @DeleteMapping("/{id}/{leaveStatus}")
    @RolesAllowed({ "ROLE_ADMINISTRATIVE_STAFF", "ROLE_PROFESSIONAL_STAFF" })
    public ResponseEntity<?> deleteOrCancelLeave(@PathVariable String id, @PathVariable LeaveStatus leaveStatus,
            Authentication authentication) {
        boolean isManager = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_MANAGER"));
        return ResponseEntity.ok(leaveService.deleteOrCancelLeave(id, leaveStatus, isManager));
    }
}