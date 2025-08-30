package com.udemine.course_manage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

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
    @Column(name = "video_url")
    String videoUrl;
    double duration;
    @Column(name = "watch_duration")
    double watchDuration;
    LocalDateTime completed_at;
    @ManyToOne
    @JoinColumn(name = "id_module", nullable = false)
    @JsonIgnore
    Module module;

    @OneToMany(mappedBy = "lesson", orphanRemoval = true)
    @JsonIgnore
    List<Exercise> Exercises;

//    @JsonProperty("lessons IT")
//    public String getNameModule(){
//        return module != null ? module.getTitle(): null;
//    }
    @PrePersist
    void  on_completed(){
        this.completed_at = LocalDateTime.now();
    }

}
