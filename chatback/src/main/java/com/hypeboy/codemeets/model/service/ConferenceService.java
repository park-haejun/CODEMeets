package com.hypeboy.codemeets.model.service;

import java.sql.SQLException;
import java.util.List;

import com.hypeboy.codemeets.model.dto.ConferenceDto;

public interface ConferenceService {
	// 회의 상세 페이지 조회
	public ConferenceDto getConferenceDetail(int conferencePk) throws Exception;

	int joinConference(int conferencePk,int userPk) throws Exception;

	int createConference(ConferenceDto conferenceDto) throws Exception;

	List<String> clickCreate(int userPk) throws Exception;

	int checkUrl(String conferenceUrl) throws Exception;

	void enterConference(int userPk, int conferencePk) throws SQLException;

	void closeConference(int conferencePk, int userPk) throws SQLException;

	void exitConference(int conferencePk, int userPk) throws SQLException;

	void joinManager(int conferencePk, int userPk) throws SQLException;

	void enterMember(int userPk, int conferencePk) throws SQLException;

	List<String> participantsConference(int conferencePk) throws SQLException;

}
