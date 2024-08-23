package com.cooksys.quiz_api.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class QuestionRequestDto {
	private String text;
	
	private List<AnswerResponseDto> answers;
	
}
