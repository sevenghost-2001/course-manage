package com.udemine.course_manage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level =  AccessLevel.PRIVATE)
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "message")
    String message;
    @Column(name = "status")
    String status;
    LocalDateTime created_at;
    LocalDateTime read_at;
    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    @JsonIgnore
    User user;

    @Transient
    @JsonProperty("notification user")
    public String getUserName() {
        return user != null ? user.getName() : null;
    }
    @PrePersist
    void on_create() {
        this.created_at = LocalDateTime.now();
    }
    void on_read() {
        this.read_at = LocalDateTime.now();
    }
}
