package com.mmt.meeting.entities;

import java.time.LocalDateTime;
import java.util.List;

public class Meeting 
{
	private MeetingRoom meetingRoom;
	
	private String subject;
	
	private String organizer;
	
	private List<String> participants;
	
	private LocalDateTime startTime;
	
	private LocalDateTime endTime;
	
	public Meeting(MeetingRoom meetingRoom, String subject, String organizer, List<String> participants,
			LocalDateTime startTime, LocalDateTime endTime) {
		super();
		this.meetingRoom = meetingRoom;
		this.subject = subject;
		this.organizer = organizer;
		this.participants = participants;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public MeetingRoom getMeetingRoom() {
		return meetingRoom;
	}

	public void setMeetingRoom(MeetingRoom meetingRoom) {
		this.meetingRoom = meetingRoom;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getOrganizer() {
		return organizer;
	}

	public void setOrganizer(String organizer) {
		this.organizer = organizer;
	}

	public List<String> getParticipants() {
		return participants;
	}

	public void setParticipants(List<String> participants) {
		this.participants = participants;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}
	
	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

}
