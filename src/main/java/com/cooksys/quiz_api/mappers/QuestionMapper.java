package com.cooksys.quiz_api.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksys.quiz_api.dtos.QuestionDto;
import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.entities.Question;

@Mapper(componentModel = "spring", uses = { AnswerMapper.class })
public interface QuestionMapper {
	
	Question requestDToEntity(QuestionRequestDto questionRequestDto);
	
	Question questionDtoToEntity(QuestionDto questionDto);
	
	QuestionResponseDto entityToDto(Question entity);

	List<Question> requestDtosToEntities(List<QuestionRequestDto> questionRequestDtos);

	List<QuestionResponseDto> entitiesToDtos(List<Question> entities);


}
