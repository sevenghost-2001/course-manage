package com.udemine.course_manage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "lessons")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level =  AccessLevel.PRIVATE)
public class Lessons {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String title;
    String video_url;
    double duration;
    double watch_duration;
    LocalDateTime completed_at;
    @ManyToOne
    @JoinColumn(name = "id_modules", nullable = false)
    @JsonIgnore
    Module module;

    @Transient
    @JsonProperty("lessons IT")
    public String getNameModule(){
        return module != null ? module.getTitle(): null;
    }
    @PrePersist
    void  on_completed(){
        this.completed_at = LocalDateTime.now();
    }

}
