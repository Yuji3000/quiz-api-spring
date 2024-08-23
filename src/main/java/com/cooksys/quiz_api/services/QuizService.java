package com.cooksys.quiz_api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.QuizResponseDto;

@Service
public interface QuizService {

	List<QuizResponseDto> getAllQuizzes();

	QuizResponseDto createQuiz(QuizRequestDto quizRequestDto);

	QuizResponseDto getQuizById(Long id);

	QuizResponseDto deleteQuiz(Long id);

	QuizResponseDto updateQuizName(Long id, String newName);

	QuestionResponseDto randomQuizQuestion(Long id);

	QuizResponseDto addQuestionToQuiz(Long id, QuestionRequestDto questionRequestDto);
}
