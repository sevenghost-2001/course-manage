package com.udemine.course_manage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lesson_comments")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class LessonsComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String content;
    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    User user; // Người dùng đã bình luận

    @ManyToOne
    @JoinColumn(name = "id_parent", nullable = true)   // cho phép null
    @JsonIgnore
    LessonsComment parentComment = null;  // Bình luận cha (nếu có)


    @ManyToOne
    @JoinColumn(name = "id_lesson", nullable = false)
    Lessons lesson; // Bài học mà bình luận thuộc về



    @Transient
    @JsonProperty("User Name")
    public String getUserName() {
        return user != null ? user.getName() : null;
    }

    @Transient
    @JsonProperty("Lesson Title")
    public String getLessonTitle() {
        return lesson != null ? lesson.getTitle() : null;
    }

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LessonsComment> replies = new ArrayList<>();

}
