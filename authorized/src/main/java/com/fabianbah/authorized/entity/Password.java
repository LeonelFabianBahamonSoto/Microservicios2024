package com.fabianbah.authorized.entity;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
@Table(name="passwords")
public class Password implements Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column(name = "passwordsid")
    private Integer passwordsId;

    @Size( min= 1, max=30, message = "La contrasenia debe tener entre 1 a 30 caracteres" )
    @NotNull( message = "La contrasenia no puede ser nulo" )
    @NotEmpty( message = "La contrasenia no puede estar vacio" )
    private String password;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "creationdate")
    private LocalDate creationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "updatedate")
    private LocalDate updateDate;

    @OneToOne( fetch = FetchType.LAZY )
    @JoinColumn( name="usersid", referencedColumnName = "usersId" )
    private User user;
}