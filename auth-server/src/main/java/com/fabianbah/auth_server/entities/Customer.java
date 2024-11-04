package com.fabianbah.auth_server.entities;

import java.io.Serializable;
import java.math.BigInteger;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    @Column(name = "id")
    private BigInteger customerId;

    private String email;

    @Column(name = "pwd")
    private String password;

    @Column(name = "rol")
    private String role;
}
