package com.mmt.meeting.controller;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mmt.meeting.entities.Meeting;
import com.mmt.meeting.entities.MeetingRequest;
import com.mmt.meeting.entities.MeetingRoom;
import com.mmt.meeting.service.MeetingAppService;

@RestController
public class MeetingAppController 
{
	@Autowired
	MeetingAppService meetingAppService;
	
	@GetMapping("/meetingRoomsByFilter")
	public List<MeetingRoom> getMeetingRooms(@RequestParam(name="date") String date, @RequestParam(name="startTime") 
					String startTime, @RequestParam(name="endTIme") String endTime, @RequestParam(name="capacity") int capacity)
	{
		return meetingAppService.getAvailableMeetingRooms(date, startTime, endTime, capacity);
	}
	
	@PutMapping("/meetingRooms")
	public MeetingRoom addMeetingRoom(@RequestParam String name, @RequestParam int capacity)
	{
		return meetingAppService.addMeetingRoom(name, capacity);
	}
	
	@GetMapping("/meetingRooms")
	public HashSet<MeetingRoom> getAllMeetingRooms()
	{
		return meetingAppService.getAllMeetingRooms();
	}

	@PostMapping("/meeting")
	public Meeting scheduleMeeting(@RequestBody MeetingRequest meeting) throws Exception
	{
		return meetingAppService.addMeeting(meeting.getMeeting_room(), meeting.getSubject(), meeting.getOrganizer(), meeting.getDate(), meeting.getStart_time(), meeting.getEnd_time(), meeting.getParticipants()); 
	}
	
	@GetMapping("/meeting/meetingRoom")
	public List<Meeting> getScheduleForMeetingRoom(@RequestParam(name="name") String name, @RequestParam("date") String date)
	{
		return meetingAppService.getScheduleForMeetingRoom(name, date);
	}
	
	@GetMapping("/meeting/employee")
	public List<Meeting> getScheduleForEmployee(@RequestParam(name="emp") String emp, @RequestParam("date") String date)
	{
		return meetingAppService.getScheduleForEmployee(emp, date);
	} 
}
