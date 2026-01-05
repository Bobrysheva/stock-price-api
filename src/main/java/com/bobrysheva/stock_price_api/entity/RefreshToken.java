package com.bobrysheva.stock_price_api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "user")
@Table(name = "refresh_tokens")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "tokens_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tokens_user_id", referencedColumnName = "user_id")
    @JsonBackReference
    private User user;

    @Column(name = "tokens_token")
    private String token;

    @Column(name = "tokens_created_at",
            nullable = false,
            updatable = false,
            columnDefinition = "TIMESTAMPTZ DEFAULT NOW()")
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "tokens_expires_at", nullable = false)
    private Instant expiresAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }
}

