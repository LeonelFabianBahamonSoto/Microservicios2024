package com.fabianbah.auth_server.entities;

import java.io.Serializable;
import java.util.Date;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
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
@Table(name = "users")
public class UserAuth implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usersid")
    private Long userId;

    @Positive(message = "El numero de identificacion debe ser positivo")
    @Column(name = "identificationid")
    private Long identificationId;

    @Size(min = 1, max = 20, message = "El nombre debe tener entre 1 a 20 caracteres")
    @NotEmpty(message = "El nombre no puede estar vacio")
    @Column(name = "fullname")
    private String fullName;

    @Size(min = 1, max = 20, message = "El apellido debe tener entre 1 a 20 caracteres")
    @NotEmpty(message = "El apellido no puede estar vacio")
    @Column(name = "lastname")
    private String lastName;

    @Size(min = 1, max = 20, message = "El correo debe tener entre 1 a 20 caracteres")
    @NotEmpty(message = "El correo no puede estar vacio")
    @Column(name = "email")
    private String userEmail;

    @Column(name = "usersenable")
    private Boolean userEnable;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "creationdate")
    private Date creationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "updatedate")
    private Date updateDate;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name="usuarios_roles", joinColumns= @JoinColumn(name="usersid"),
	inverseJoinColumns=@JoinColumn(name="rolesid"),
	uniqueConstraints= {@UniqueConstraint(columnNames= {"usersid", "rolesid"})})
	private List<Role> roles;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "rolesid", referencedColumnName = "roleId")
    private List<Role> roleId;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    private Password password;
}
