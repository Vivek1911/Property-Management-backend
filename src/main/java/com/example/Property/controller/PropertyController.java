package com.example.Property.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.Property.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Property.model.Property;
import com.example.Property.service.PropertyService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/property")
public class PropertyController {

    @Autowired
    private PropertyService Ps;

    @PostMapping("/post")
    public ResponseEntity<Map<String, Object>> saveProperty(@RequestBody Property property) {
        if (property.getUser() == null && property.getUserId() != null) {
            User user = new User();
            user.setId(property.getUserId()); // Set only the ID
            property.setUser(user);
        }

        Property saved = Ps.saveProperty(property);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Property saved successfully!");
        response.put("property", saved);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateProperty(@PathVariable Long id, @RequestBody Property property) {
        if (property.getUser() == null && property.getUserId() != null) {
            User user = new User();
            user.setId(property.getUserId());
            property.setUser(user);
        }

        Property updated = Ps.updateProperty(id, property);
        if (updated == null) {
            return ResponseEntity.status(404).body("Property not found");
        }
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Property updated successfully!");
        response.put("property", updated);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteProperty(@PathVariable Long id) {
        boolean deleted = Ps.deleteProperty(id);
        if (!deleted) {
            return ResponseEntity.status(404).body("Property not found");
        }
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Property deleted successfully!");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllProperties() {
        List<Property> properties = Ps.getAllProperties();
        if (properties.isEmpty()) {
            return ResponseEntity.status(404).body("No properties found");
        }
        return ResponseEntity.ok(properties);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPropertyById(@PathVariable Long id) {
        Property property = Ps.getPropertyById(id);
        if (property == null) {
            return ResponseEntity.status(404).body("Property not found");
        }
        return ResponseEntity.ok(property);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getPropertiesByUserId(@PathVariable Long userId) {
        List<Property> properties = Ps.getPropertiesByUserId(userId);
        if (properties.isEmpty()) {
            return ResponseEntity.status(404).body("No properties found for this user");
        }
        return ResponseEntity.ok(properties);
    }
}
