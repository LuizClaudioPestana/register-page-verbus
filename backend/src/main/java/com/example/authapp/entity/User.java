package com.example.authapp.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false, length = 120)
    public String fullName;

    @Column(nullable = false, unique = true, length = 150)
    public String email;

    @Column(nullable = false, length = 255)
    public String passwordHash;

    @Column(length = 8)
    public String cep;

    @Column(length = 180)
    public String street;

    @Column(length = 20)
    public String number;

    @Column(length = 120)
    public String complement;

    @Column(length = 120)
    public String neighborhood;

    @Column(length = 120)
    public String city;

    @Column(length = 2)
    public String state;

    @Column(length = 1000)
    public String additionalInfo;

    @Column(length = 1000)
    public String notes;

    @Column(length = 120)
    public String resetToken;

    public LocalDateTime resetTokenExpiresAt;

    @Column(nullable = false)
    public LocalDateTime createdAt;

    @Column(nullable = false)
    public LocalDateTime updatedAt;
}