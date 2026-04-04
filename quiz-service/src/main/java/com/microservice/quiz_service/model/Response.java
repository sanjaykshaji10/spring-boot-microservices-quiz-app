package com.microservice.quiz_service.model;

import lombok.Data;

@Data
public class Response {
    private Integer questionId;
    private String selectedOption;
}
