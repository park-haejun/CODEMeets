package com.hypeboy.codemeets.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QnaAnswerDto {

	private int groupQnaAnswerPk;
	private String groupQnaAnswerContents;
	private String groupQnaAnswerDate;
	private int groupQnaAnswerUpdate;
	private int groupQuestionPk;
	private int userPk;
	
	// 게시글 작성자 닉네임
	private String username;
	// like 수
	private int groupQnaAnswerLikeCnt;
	// 회원의 like 유무	
	private int groupQnaAnswerLike;
	// 글 수
	private int total;
}
