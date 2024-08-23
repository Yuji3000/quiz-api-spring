package com.cooksys.quiz_api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.QuizResponseDto;
import com.cooksys.quiz_api.entities.Quiz;
import com.cooksys.quiz_api.exceptions.BadRequestException;
import com.cooksys.quiz_api.exceptions.NotFoundException;
import com.cooksys.quiz_api.mappers.QuizMapper;
import com.cooksys.quiz_api.repositories.QuizRepository;
import com.cooksys.quiz_api.services.QuizService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

  private final QuizRepository quizRepository;
  private final QuizMapper quizMapper;

  
	private void validateQuizRequest(QuizRequestDto quizRequestDto) {
		if (quizRequestDto.getName() == null ) {
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

}
