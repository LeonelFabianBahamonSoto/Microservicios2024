package com.fabianbah.auth_server.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
@Table(name = "roles")
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rolesid")
    private Long rolesId;

    @Column(name = "rolename")
    private String roleName;

    @Column(name = "roledescription")
    private String roleDescription;

    // // @ManyToOne
    // // @JoinColumn(name = "customerid")
    // @ManyToMany(mappedBy = "roles")
    // private List<Customer> customers;
    // private Customer customer;
}