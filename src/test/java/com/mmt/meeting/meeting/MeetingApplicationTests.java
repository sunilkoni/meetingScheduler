package com.mmt.meeting.meeting;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mmt.meeting.service.MeetingAppService;

@SpringBootTest
class MeetingApplicationTests 
{
	@Autowired
	MeetingAppService meetingAppService;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	public void addMeetingRoom_Success()
	{
		meetingAppService.addMeetingRoom("Narmada", 10);
		meetingAppService.addMeetingRoom("Tapi", 5);
		assertTrue(meetingAppService.meetingRooms.size() == 2);
		meetingAppService.meetingRooms.clear();
	}
	
	@Test
	public void addMeeting_Success()
	{
		List<String> participants = new ArrayList<String>();
		participants.add("MMT6373");
		participants.add("MMT6474");
		participants.add("MMT6575");
		meetingAppService.addMeetingRoom("Narmada", 2);
		try {
			meetingAppService.addMeeting("Narmada", "Sprint Planning", "MMT6373", "2019-11-10", "10:00", 
					"11:00", participants);
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("size exceeds meeting room capacity"));
		}
		meetingAppService.meetings.clear();
		meetingAppService.meetingRooms.clear();
	}
	
	@Test
	public void testGetMeetingsForEmployee()
	{
		List<String> participants = new ArrayList<String>();
		participants.add("MMT6373");
		participants.add("MMT6474");
		//participants.add("MMT6575");
		meetingAppService.addMeetingRoom("Narmada", 2);
		try {
			meetingAppService.addMeeting("Narmada", "Sprint Planning", "MMT6373", "2019-11-10", "10:00", 
					"11:00", participants);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(meetingAppService.getScheduleForEmployee("MMT6373", "2019-11-10").size()==1);
		meetingAppService.meetings.clear();
		meetingAppService.meetingRooms.clear();
	}
	
	@Test
	public void testGetMeetingsForRoom()
	{
		List<String> participants = new ArrayList<String>();
		participants.add("MMT6373");
		participants.add("MMT6474");
		//participants.add("MMT6575");
		meetingAppService.addMeetingRoom("Narmada", 2);
		try {
			meetingAppService.addMeeting("Narmada", "Sprint Planning", "MMT6373", "2019-11-10", "10:00", 
					"11:00", participants);
			meetingAppService.addMeeting("Narmada", "Sprint Planning", "MMT6373", "2019-11-10", "13:00", 
					"14:00", participants);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(meetingAppService.getScheduleForMeetingRoom("Narmada", "2019-11-10").size()==2);
		meetingAppService.meetings.clear();
		meetingAppService.meetingRooms.clear();
	}
	
}
