package com.microservice.question_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="question", schema="myschema")
@Data
public class Question {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Integer id;

    @Column(name="question_title")
    public String question;

    @Column(name="option1")
    public String optionA; 

    @Column(name="option2")
    public String optionB;

    @Column(name="option3")
    public String optionC;

    @Column(name="option4")
    public String optionD;

    @Column(name="right_answer")
    public String rightAnswer;

    @Column(name="difficultylevel")
    public String difficultyLevel;

    @Column(name="category")
    public String category;

}
