package com.example.practice.enity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_gen")
    @SequenceGenerator(name = "post_gen", sequenceName = "post_seq", allocationSize = 1)
    private Long id;
    private String image;
    private String text;
    private String title;
    private LocalDateTime localDateTime;

}
