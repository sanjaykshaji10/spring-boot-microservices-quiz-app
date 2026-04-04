package com.microservice.question_service.model;

import lombok.Data;

@Data
public class QuestionWrapper {
        
    public Integer id;
    public String question;
    public String optionA; 
    public String optionB;
    public String optionC;
    public String optionD;
    
    public QuestionWrapper(Integer id, String question, String optionA, String optionB, String optionC,
            String optionD) {
        this.id = id;
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
    }

    
}
