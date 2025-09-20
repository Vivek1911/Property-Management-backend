package com.example.Property.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Property.model.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    
    List<Property> findByUser_Id(Long userId);
}
