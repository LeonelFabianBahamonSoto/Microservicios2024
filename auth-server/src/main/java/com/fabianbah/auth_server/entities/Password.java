package com.fabianbah.auth_server.entities;

import java.io.Serializable;
import java.util.Date;

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
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@Entity
@NoArgsConstructor
@Table(name = "passwords")
public class Password implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passwordsid")
    private Integer passwordsId;

    @NotEmpty(message = "La contrasenia no puede estar vacia")
    @Size(min = 5, max = 30, message = "La contrasenia debe contener minimo 5 caracteres y maximo 30")
    @Column(name = "passworrd")
    private String userPassword;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "creationdate")
    private Date creationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "updatedate")
    private Date updateDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usersid", referencedColumnName = "userId")
    private UserAuth user;
}