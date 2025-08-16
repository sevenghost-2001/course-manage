package com.udemine.course_manage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String content;
    LocalDateTime send_at;
    @OneToOne
    @JoinColumn(name = "sender_id", nullable = false)
    @JsonIgnore
    User sender;

    @OneToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    @JsonIgnore
    User receiver;

    @Transient
    @JsonProperty("Sender")
    public String getSender() { return sender != null? sender.getName() : null; }

    @Transient
    @JsonProperty("Receiver")
    public String getReceiver() { return receiver != null? receiver.getName() : null; }

    @PrePersist // Automatically set the send_at field to the current time when a new message is created
    public void onCreate() {
        this.send_at = LocalDateTime.now();
    }
}
