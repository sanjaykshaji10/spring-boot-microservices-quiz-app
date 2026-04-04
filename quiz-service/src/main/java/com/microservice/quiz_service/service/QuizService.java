package com.microservice.quiz_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.microservice.quiz_service.dao.QuizDao;
import com.microservice.quiz_service.feign.QuizInterface;
import com.microservice.quiz_service.model.QuestionWrapper;
import com.microservice.quiz_service.model.Quiz;
import com.microservice.quiz_service.model.Response;

@Service
public class QuizService {
    
    @Autowired
    QuizDao quizDao;

    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, int numQuestions, String title) {
        
        List<Integer> questions = quizInterface.generateQuiz(category, numQuestions).getBody();

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizDao.save(quiz);
        
        return ResponseEntity.ok("Quiz created successfully");
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer quizId) {
        Optional<Quiz> quiz = quizDao.findById(quizId);
        List<Integer> questionIds = quiz.get().getQuestionIds();

         ResponseEntity<List<QuestionWrapper>> responseEntity = quizInterface.getQuestionsById(questionIds);
         return responseEntity;
    }

    public ResponseEntity<Integer> calculateResult(List<Response> responses, Integer quizId) {
        // Quiz quiz = quizDao.findById(quizId).get();

        ResponseEntity<Integer> resultEntity = quizInterface.getScore(responses);
        return resultEntity;

    }


}
