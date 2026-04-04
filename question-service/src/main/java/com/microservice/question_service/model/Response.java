package com.microservice.question_service.model;

import lombok.Data;

@Data
public class Response {
    private Integer questionId;
    private String selectedOption;
}
