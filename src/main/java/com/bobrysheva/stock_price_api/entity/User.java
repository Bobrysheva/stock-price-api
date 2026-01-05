package com.bobrysheva.stock_price_api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"loadedDatas", "refreshTokens"})
@Table(name = "users")
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_login", nullable = false, length = 50)
    @NotBlank(message = "Username is mandatory")
    @Size(min = 3, max = 50, message = "Username must be 3-50 characters")
    private String login;

    @Column(name = "user_email", nullable = false, unique = true, length = 100)
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @Column(name = "user_password", nullable = false, length = 100)
    @NotBlank(message = "Password is mandatory")
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    @Column(name = "user_created_at", updatable = false)
    private java.time.LocalDateTime createdAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_stock_data",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "user_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "data_id",
                    referencedColumnName = "stock_data_id"
            )
    )
    @JsonManagedReference
    private Set<StockData> loadedDatas = new HashSet<>();

    @OneToMany(mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true)
    @JsonBackReference
    private Set<RefreshToken> refreshTokens = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        createdAt = java.time.LocalDateTime.now();
    }

}