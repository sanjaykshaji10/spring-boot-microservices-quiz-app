package com.microservice.question_service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.microservice.question_service.dao.QuestionDAO;
import com.microservice.question_service.model.Question;
import com.microservice.question_service.model.QuestionWrapper;
import com.microservice.question_service.model.Response;

@Service
public class QuestionService {

    @Autowired
    QuestionDAO questionDAO;

    public ResponseEntity<List<Question>> getAllQuestions() {
      try {
         return new ResponseEntity<>(questionDAO.findAll(), HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
        }
         return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
         return new ResponseEntity<>(questionDAO.findByCategoryIgnoreCase(category), HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
        }
         return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
     }

     public ResponseEntity<List<Question>> getQuestionsByCategoryAndDifficulty(String category, String difficultyLevel) {
        try {
         return new ResponseEntity<>(questionDAO.findByCategoryandDifficultyLevel(category, difficultyLevel), HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
        }
         return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
     }

     public ResponseEntity<String> addQuestion(Question question) {
         questionDAO.save(question);
         return new ResponseEntity<>("Question added successfully", HttpStatus.CREATED);
     }

     public ResponseEntity<List<Integer>> getQuestionsForQuiz(String category, Integer numQ) {

        List<Integer> quIntegers = questionDAO.findRandomQuestionsByCategory(category, numQ);

        return new ResponseEntity<>(quIntegers, HttpStatus.OK);
     }

     public ResponseEntity<List<QuestionWrapper>> getQuestionsByIds(List<Integer> questionIds) {
        List<QuestionWrapper> wrappers = new ArrayList<>();
        for(Integer id : questionIds) {
            Question question = questionDAO.findById(id).get();
            wrappers.add(new QuestionWrapper(question.getId(),
             question.getQuestion(), question.getOptionA(), question.getOptionB(), question.getOptionC(), question.getOptionC()));
        }

        return new ResponseEntity<>(wrappers, HttpStatus.OK);
     }

     public ResponseEntity<Integer> getScore(List<Response> responses) {
        int right = 0;

        for(Response response : responses) {
            Question question = questionDAO.findById(response.getQuestionId()).get();

            if(response.getSelectedOption().equals(question.getRightAnswer())) {
                right++;
            }
        }

        return new ResponseEntity<>(right, HttpStatus.OK);
     }
}
