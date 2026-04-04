package com.microservice.quiz_service.model;

import lombok.Data;

@Data
public class QuizDto {
    String category;
    int numQuestions;
    String title;
}
