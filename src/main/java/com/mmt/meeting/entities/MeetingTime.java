package com.mmt.meeting.entities;

import java.time.LocalDateTime;

public class MeetingTime 
{
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private int capacity;
	public MeetingTime(LocalDateTime startTime, LocalDateTime endTime) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
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
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	

}
