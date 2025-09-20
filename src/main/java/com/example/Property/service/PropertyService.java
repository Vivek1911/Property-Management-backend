package com.example.Property.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Property.model.Property;
import com.example.Property.repository.PropertyRepository;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository Pr;

    public Property saveProperty(Property property) {
        Pr.save(property);
        return property;
    }

    public Property updateProperty(Long id, Property property) {
        Property existing = Pr.findById(id).orElse(null);
        if(existing == null) return null;
        existing.setTitle(property.getTitle());
        existing.setDescription(property.getDescription());
        existing.setAddress(property.getAddress());
        existing.setPrice(property.getPrice());
        existing.setImagePath(property.getImagePath());
        Pr.save(existing);
        return existing;
    }

    public boolean deleteProperty(Long id) {
        if(!Pr.existsById(id)) return false;
        Pr.deleteById(id);
        return true;
    }

    public Property getPropertyById(Long id) {
        return Pr.findById(id).orElse(null);
    }
    
    public List<Property> getPropertiesByUserId(Long userId) {
        return Pr.findByUser_Id(userId);
    }

    public List<Property> getAllProperties() {
        return Pr.findAll();
    }
}
