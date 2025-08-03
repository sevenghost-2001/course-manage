//package com.udemine.course_manage.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.experimental.FieldDefaults;
//
//@Entity
//@Table(name = "teachs")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
//public class Teach {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    int id;
//    @ManyToOne
//    @JoinColumn(name = "id_course", nullable = false)
//    Course course;
//    @ManyToOne
//    @JoinColumn(name = "id_user", nullable = false)
//    User instructor;
//}
