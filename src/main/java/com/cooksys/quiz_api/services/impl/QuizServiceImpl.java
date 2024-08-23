package com.cooksys.quiz_api.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.QuizResponseDto;
import com.cooksys.quiz_api.entities.Question;
import com.cooksys.quiz_api.entities.Quiz;
import com.cooksys.quiz_api.exceptions.BadRequestException;
import com.cooksys.quiz_api.exceptions.NotFoundException;
import com.cooksys.quiz_api.mappers.QuestionMapper;
import com.cooksys.quiz_api.mappers.QuizMapper;
import com.cooksys.quiz_api.repositories.QuizRepository;
import com.cooksys.quiz_api.services.QuizService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class QuizServiceImpl implements QuizService {

	private final QuizRepository quizRepository;
	private final QuizMapper quizMapper;
	private final QuestionMapper questionMapper;

	private void validateQuizRequest(QuizRequestDto quizRequestDto) {
		if (quizRequestDto.getName() == null) {
			throw new BadRequestException("All Fields required for creating a quiz request dto");
		}
	}

	private Quiz getQuiz(Long id) {
		Optional<Quiz> optionalQuiz = quizRepository.findById(id);

		if (optionalQuiz.isEmpty()) {
			throw new NotFoundException("No Quiz found with ID:" + id);
		}
		return optionalQuiz.get();
	}

	@Override
	public QuizResponseDto getQuizById(Long id) {
		return quizMapper.entityToDto(getQuiz(id));
	}

	@Override
	public List<QuizResponseDto> getAllQuizzes() {
		return quizMapper.entitiesToDtos(quizRepository.findAll());
	}

	@Override
	public QuizResponseDto createQuiz(QuizRequestDto quizRequestDto) {
		validateQuizRequest(quizRequestDto);
		Quiz quizToSave = quizMapper.requestDtoToEntity(quizRequestDto);
		return quizMapper.entityToDto(quizRepository.saveAndFlush(quizToSave));
	}

	@Override
	public QuizResponseDto deleteQuiz(Long id) {
		Quiz quizToDelete = getQuiz(id);
		quizRepository.delete(quizToDelete);

		return quizMapper.entityToDto(quizToDelete);
	}

	@Override
	public QuizResponseDto updateQuizName(Long id, String newName) {
		Quiz quizToUpdate = getQuiz(id);
		quizToUpdate.setName(newName);

		return quizMapper.entityToDto(quizRepository.saveAndFlush(quizToUpdate));
	}

	@Override
	public QuestionResponseDto randomQuizQuestion(Long id) {
		Quiz selectedQuiz = getQuiz(id);
		List<Question> questions = selectedQuiz.getQuestions();
		if (questions == null || questions.isEmpty()) {
			throw new NotFoundException("No questions found for Quiz ID: " + id);
		}
		int randomIndex = ThreadLocalRandom.current().nextInt(questions.size());
		Question randomQuestion = questions.get(randomIndex);

		return questionMapper.entityToDto(randomQuestion);
	}

	@Override
	public QuizResponseDto addQuestionToQuiz(Long id, QuestionRequestDto questionRequestDto) {
		Quiz quiz = getQuiz(id);
		if (questionRequestDto.getText() == null) {
			throw new BadRequestException("Text Field required for adding a question Dto to a quiz");
		}
		
		   Question questionToAdd = questionMapper.requestDToEntity(questionRequestDto);
		    questionToAdd.setQuiz(quiz);
		    questionToAdd.setText(questionRequestDto.getText());

		    List<Question> questions = quiz.getQuestions();
		    if (questions == null) {
		        questions = new ArrayList<>();
		    }
		    questions.add(questionToAdd);		    
		    quiz.setQuestions(questions);
		    
		    Quiz updatedQuiz = quizRepository.saveAndFlush(quiz);
		    return quizMapper.entityToDto(updatedQuiz);
		}
		

}
