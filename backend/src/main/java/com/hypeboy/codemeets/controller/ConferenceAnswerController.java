package com.hypeboy.codemeets.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hypeboy.codemeets.model.dto.ConferenceAnswerDto;
import com.hypeboy.codemeets.model.service.ConferenceAnswerService;
import com.hypeboy.codemeets.utils.JwtTokenProvider;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@Api(tags="회의내 질문 답변")
@RequestMapping("/api/conferenceAnswer")
public class ConferenceAnswerController {
	
	private static final Logger Logger = LoggerFactory.getLogger(QnaController.class);
	
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	private ConferenceAnswerService service;
	
	@Autowired
	public ConferenceAnswerController(ConferenceAnswerService service) {
		super();
		this.service = service;
	}
	
	@Operation(summary = "회의 내 질문 답변", description= "답변 작성 API" + "conferenceQuestionPk, content, userPk 만 입력해주시면 됩니다")
	@PostMapping
	public ResponseEntity<String> writeConferenceAnswer(@ApiParam(value = "conferenceAnswer 작성", required= true) @RequestBody ConferenceAnswerDto conferenceAnswerDto) throws Exception {
		
		Logger.info("writeConferenceAnswer - 호출");
		try {
			service.writeConferenceAnswer(conferenceAnswerDto);
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			Logger.warn("writeConferenceAnswer fail -" + e);
			return new ResponseEntity<String>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@Operation(summary = "회의 내 답변 목록", description = "회의 질문 답변 리스트")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "ACCESS_TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    })
	@GetMapping
	public ResponseEntity<?> getList(HttpServletRequest request, @RequestParam("conferenceQuestionPk") int conferenceQuestionPk) throws Exception {
		
		Logger.info("conferenceAnswer List 호출");
		int userPk = 0;
		if (jwtTokenProvider.validateToken(request.getHeader("access_token"))) {
			Logger.info("사용가능한 토큰입니다 ");
			
			userPk = jwtTokenProvider.getUserPk(request.getHeader("access_token"));
			Logger.info("userPk - " + userPk);
		}
		else {
			Logger.info("토큰 실패");
		}
		
		try {
			List<ConferenceAnswerDto> conferenceAnswerList = service.getList(conferenceQuestionPk, userPk);
			return new ResponseEntity<List<ConferenceAnswerDto>>(conferenceAnswerList, HttpStatus.OK);
		} catch (Exception e) {
			Logger.warn("Controller conferenceAnswerList fail - " + e);
			return new ResponseEntity<String>("server error", HttpStatus.OK);
		}
	}
	
	@PutMapping
	public ResponseEntity<String> modifyconferenceAnswer(@RequestBody ConferenceAnswerDto conferenceAnswerDto) throws Exception {
		Logger.info("modiyconferenceAnswer 호출");
		try {
			service.modifyConferenceAnswer(conferenceAnswerDto);
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} catch (Exception e){
			Logger.info("modifyconferenceAnswer fail - " + e);
			return new ResponseEntity<String>("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteConferenceAnswer(@RequestParam("conferenceAnswerPk") int conferenceAnswerPk) throws Exception {
		Logger.info("Controller deleteConferenceAnswer - 호출");
		
		try {
			service.deleteConferenceAnswer(conferenceAnswerPk);
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			Logger.warn("controller deleteConferenceAnswer fail - " + e);
			return new ResponseEntity<String>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/Like")
	public ResponseEntity<String> likeConferenceAnswer(@RequestBody ConferenceAnswerDto conferenceAnswerDto) throws Exception {
		
		Logger.info("likeConferenceAnswer 호출");
		try {
			service.likeConferenceAnswer(conferenceAnswerDto);
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			Logger.warn("likeConferenceAnswer fail - " + e);
			return new ResponseEntity<String>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
