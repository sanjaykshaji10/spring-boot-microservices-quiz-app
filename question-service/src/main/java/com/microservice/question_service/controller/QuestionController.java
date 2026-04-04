package com.microservice.question_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.question_service.model.Question;
import com.microservice.question_service.service.QuestionService;

import org.springframework.web.bind.annotation.RequestParam;

import com.microservice.question_service.model.QuestionWrapper;
import com.microservice.question_service.model.Response;



@RestController
@RequestMapping("questions")
public class QuestionController {

    @Autowired
    QuestionService questionService;
    
    @GetMapping("/all")
    public ResponseEntity<List<Question>> allQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/category/{cat}")
    public ResponseEntity<List<Question>>  getQuestionsByCategory(@PathVariable("cat") String category) {
        return questionService.getQuestionsByCategory(category);
    }

    @GetMapping("/category/{cat}/{diff}")
    public ResponseEntity<List<Question>>  getQuestionsByCategoryAndDifficulty(@PathVariable("cat") String category, @PathVariable("diff") String difficultyLevel) {
        return questionService.getQuestionsByCategoryAndDifficulty(category, difficultyLevel);
     }

     @PostMapping("add")
     public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }


    @GetMapping("generate")
    public ResponseEntity<List<Integer>> generateQuiz(@RequestParam String category, @RequestParam Integer numQ) {
        return questionService.getQuestionsForQuiz(category, numQ);
    }
    

    @PostMapping("getQuestion")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsById(@RequestBody List<Integer> questionIds) {
        return questionService.getQuestionsByIds(questionIds);
    }


    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses) {
        return questionService.getScore(responses);
    }
}
