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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "partners")
public class Partner implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "partnersid")
    private Long partnerId;

    @Column(name = "client_id") //Spring por debajo pone el _ por lo que se puede omitir el Column igual lo puse.
    private String clientId;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "client_secret")
    private String clientSecret;

    private String scopes;

    @Column(name = "grant_types")
    private String grantTypes;

    @Column(name = "authentication_methods")
    private String authenticationMethods;

    @Column(name = "redirect_uri")
    private String redirectUri;

    @Column(name = "redirect_uri_logout")
    private String redirectUriLogout;
}