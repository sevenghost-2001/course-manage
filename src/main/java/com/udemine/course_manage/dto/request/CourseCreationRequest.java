package com.udemine.course_manage.dto.request;

import com.udemine.course_manage.entity.Status;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CourseCreationRequest {
    @Size(min = 10,message = "Title must be at least 10 characters")
    private String title;
    private String description;
    private int category_id;
    private int instructor_id;
    private BigDecimal price;
    private Status status;
    private String review_notes;
    private LocalDateTime created_at;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getInstructor_id() {
        return instructor_id;
    }

    public void setInstructor_id(int instructor_id) {
        this.instructor_id = instructor_id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getReview_notes() {
        return review_notes;
    }

    public void setReview_notes(String review_notes) {
        this.review_notes = review_notes;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
}
