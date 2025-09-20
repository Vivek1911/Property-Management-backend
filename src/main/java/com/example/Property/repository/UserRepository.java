package com.example.Property.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Property.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    @Query("SELECT COUNT(U) FROM User U WHERE U.email = :email AND U.password = :password")
    int validateCredentials(@Param("email") String email, @Param("password") String password);
    

    @Query("select U from User U where U.email=:email AND U.password=:password")
    public User findByEmailAndPassword(String email, String password);

}
