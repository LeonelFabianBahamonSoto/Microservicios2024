package com.fabianbah.auth_server.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
@Table(name = "customers")
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerid")
    private Long customerId;

    private String email;

    @Column(name = "pwd")
    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER) // Cambiar a @ManyToMany
    @JoinTable(
        name = "customer_roles", // Nombre de la tabla de rompimiento
        joinColumns = @JoinColumn(name = "customerid"), // Columna en la tabla customers
        inverseJoinColumns = @JoinColumn(name = "rolesid") // Columna en la tabla roles
    )
    private List<Role> roles;
}