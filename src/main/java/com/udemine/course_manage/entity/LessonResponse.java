package com.udemine.course_manage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "lesson_responses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String content;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false) // nullable = false ensures that the user field cannot be null
    @JsonIgnore
    User user; // User who created the response

    @ManyToOne
    @JoinColumn(name = "id_lesson_chat", nullable = false)
    @JsonIgnore
    LessonComment lessonComment;

    @Transient
    @JsonProperty("User")
    public String getUser() { return user != null? user.getName() : null; }

    @Transient
    @JsonProperty("LessonComment")
    public String getLessonComment() { return lessonComment != null? lessonComment.getContent() : null; }

}
