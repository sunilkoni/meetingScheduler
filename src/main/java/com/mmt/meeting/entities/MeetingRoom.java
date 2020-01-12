package com.mmt.meeting.entities;

public class MeetingRoom 
{
	private String name;
	
	private int capacity;

	
	public MeetingRoom(String name, int capacity) {
		super();
		this.name = name;
		this.capacity = capacity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	@Override
	public String toString() {
		return "MeetingRoom [name=" + name + ", capacity=" + capacity + "]";
	}
	
}
