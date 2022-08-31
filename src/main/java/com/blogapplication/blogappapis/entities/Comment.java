package com.blogapplication.blogappapis.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "column_id")
    private Integer id;

    @Column(nullable = false, length = 5000)
    private String content;

    // ============== Defining Relationship ========================
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    private User user;

}
