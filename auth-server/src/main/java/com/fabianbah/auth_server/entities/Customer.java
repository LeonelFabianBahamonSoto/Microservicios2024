package com.fabianbah.auth_server.entities;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

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
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customersid")
    private long customerId;

    @Column(name = "identificationid")
    @NotNull(message = "El numero de identificacion no puede ser nulo")
    @Positive(message = "El numero de identificacion debe ser positivo")
    private long identificationId;

    @Column(name = "fullname")
    @NotEmpty(message = "El nombre no puede estar vacio")
    @NotNull(message = "El nombre no puede ser nulo")
    @Size(min = 2, max = 20, message = "El nombre debe tener entre 1 a 20 caracteres")
    private String fullName;

    @Column(name = "lastname")
    @NotNull(message = "El apellido no puede ser nulo")
    @NotEmpty(message = "El apellido no puede estar vacio")
    @Size(min = 2, max = 20, message = "El apellido debe contener entre 2 y 20 caracteres")
    private String lastName;

    @NotNull(message = "El email no puede ser nulo")
    @NotEmpty(message = "El email no puede estar vacio")
    @Size(min = 6, message = "El email debe contener un minimo de 6 caracteres")
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "creationdate")
    private LocalDate creationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "updatedate")
    private LocalDate updateDate;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER) // Cambiar a @ManyToMany
    @JoinTable(name = "customer_roles", // Nombre de la tabla de rompimiento
            joinColumns = @JoinColumn(name = "customersid"), // Columna en la tabla customers
            inverseJoinColumns = @JoinColumn(name = "rolesid") // Columna en la tabla roles
    )
    private List<Role> roles;
}
