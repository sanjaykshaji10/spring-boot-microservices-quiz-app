package com.microservice.question_service.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.microservice.question_service.model.Question;

@Repository
public interface QuestionDAO extends JpaRepository<Question, Integer>{

    public List<Question> findByCategoryIgnoreCase(String category);

    @Query("SELECT q FROM Question q WHERE LOWER(q.category) = LOWER(:category) AND LOWER(q.difficultyLevel) = LOWER(:difficultyLevel)")
    public List<Question> findByCategoryandDifficultyLevel(String category, String difficultyLevel);

    @Query(value = "SELECT q.id FROM myschema.question q WHERE LOWER(q.category) = LOWER(:category) ORDER BY RANDOM() LIMIT :numQuestions", nativeQuery = true)
    public List<Integer> findRandomQuestionsByCategory(String category, int numQuestions);  
    
}
