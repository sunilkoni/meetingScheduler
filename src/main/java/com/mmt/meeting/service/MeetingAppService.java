package com.mmt.meeting.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mmt.meeting.entities.Meeting;
import com.mmt.meeting.entities.MeetingRoom;
import com.mmt.meeting.entities.MeetingTime;

@Service
public class MeetingAppService 
{
	public HashSet<MeetingRoom> meetingRooms = new HashSet<MeetingRoom>();
	
	public List<Meeting> meetings = new LinkedList<Meeting>();
	
	public Map<String, List<MeetingTime>> meetingMap = new HashMap<String, List<MeetingTime>>(); 
	
	public MeetingRoom addMeetingRoom(String name, int capacity)
	{
		MeetingRoom m = new MeetingRoom(name, capacity);
		meetingRooms.add(m);
		return m;
	}
	
	public Meeting addMeeting(String name, String subject, String organizer, String date, String start, String end, List<String> participants) throws Exception
	{
		String startTime = date+"T"+start+":00";
		String endTIme = date+"T"+end+":00";
		LocalDateTime scheduleStartTime = LocalDateTime.parse(startTime);
		LocalDateTime scheduleEndTime = LocalDateTime.parse(endTIme);
		validateMeetingRoom(name, participants.size(), scheduleStartTime, scheduleEndTime);
		MeetingRoom mr = new MeetingRoom(name, participants.size());
		Meeting m = new Meeting(mr, subject, organizer, participants, scheduleStartTime, scheduleEndTime);
		addToMeetingMap(mr, scheduleStartTime, scheduleEndTime);
		meetings.add(m);
		return m;
	}
	
	private void addToMeetingMap(MeetingRoom mr, LocalDateTime scheduleStartTime, LocalDateTime scheduleEndTime) 
	{
		MeetingTime mt = new MeetingTime(scheduleStartTime, scheduleEndTime);
		List<MeetingTime> mts = meetingMap.get(mr);
		if (mts != null) {
			mts.add(mt);
		}
		else
		{
			mts = Arrays.asList(mt);
		}
		meetingMap.put(mr.getName(), mts);
	}

	private void validateMeetingRoom(String name, int size, LocalDateTime scheduleStartTime, LocalDateTime scheduleEndTime) throws Exception {
		Optional<MeetingRoom> mr = meetingRooms.stream().filter(m -> m.getName().equals(name)).findAny();
		if(mr.isPresent())
		{
			MeetingRoom curMr = mr.get();
			if(curMr.getCapacity()< size)
				throw new Exception("size exceeds meeting room capacity");
			
			Optional<Meeting> mrOpt = meetings.stream()
								.filter(mroom -> mroom.getMeetingRoom().getName().equals(name))
								.filter(mroom -> scheduleEndTime.isEqual(mroom.getEndTime()) || scheduleStartTime.isAfter(mroom.getStartTime()))
								.filter(mroom -> scheduleEndTime.isEqual(mroom.getEndTime()) || scheduleEndTime.isBefore(mroom.getEndTime()))
								.findAny();
			if(mrOpt.isPresent())
				throw new Exception("Meeting is already scheduled at this time");
		}
		else
			throw new Exception("Meeting room "+name+" does not exist");
	}

	public List<Meeting> getMeetings(String date, String start, String end, int capacity)
	{
		String startTime = date+"T"+start+":00";
		String endTime = date+"T"+end+":00";
		LocalDateTime scheduleStartTime = LocalDateTime.parse(startTime);
		LocalDateTime scheduleEndTime = LocalDateTime.parse(endTime);
		Predicate<Meeting> inBetween = m -> m.getStartTime().isAfter(scheduleStartTime) && m.getEndTime().isBefore(scheduleEndTime);
		List<Meeting> resultMeetings = meetings.stream()
				.filter(m -> m.getMeetingRoom().getCapacity() == capacity)
				.filter(inBetween)
				.collect(Collectors.toList());
		return resultMeetings;
	}
	
	public List<MeetingRoom> getAvailableMeetingRooms(String date, String start, String end, int capacity)
	{
		List<MeetingRoom> availableMeetingRooms = new ArrayList<MeetingRoom>();
		String startTime = date+"T"+start+":00";
		String endTIme = date+"T"+end+":00";
		LocalDateTime scheduleStartTime = LocalDateTime.parse(startTime);
		LocalDateTime scheduleEndTime = LocalDateTime.parse(endTIme);
		for(MeetingRoom mr : meetingRooms)
		{
			List<MeetingTime> mts = meetingMap.get(mr.getName());
			if(mts == null && mr.getCapacity() >= capacity)
			{
				availableMeetingRooms.add(mr);
				continue;
			}
			if (capacity<mr.getCapacity()) {
				Optional<MeetingTime> mtOpt = mts.stream()
						.filter(mt -> scheduleStartTime.isEqual(mt.getStartTime()) || scheduleEndTime.isAfter(mt.getStartTime()))
						.filter(mt -> scheduleEndTime.isEqual(mt.getEndTime())
								|| scheduleEndTime.isBefore(mt.getEndTime()))
						.findAny();
				if (mtOpt.isPresent())
					continue;
				availableMeetingRooms.add(mr);
			}
			else
				continue;
		}
		return availableMeetingRooms;
	}
	
	public List<Meeting> getScheduleForMeetingRoom(String name, String date)
	{
		String startTime = date+"T"+"00:00";
		LocalDateTime scheduleStartTime = LocalDateTime.parse(startTime);
		List<Meeting> resultMeetings = meetings.stream()
									.filter(m -> m.getMeetingRoom().getName().equals(name))
									.filter(m -> m.getStartTime().isAfter(scheduleStartTime))
									.collect(Collectors.toList());
		return resultMeetings;
	}
	
	public List<Meeting> getScheduleForEmployee(String emp, String date)
	{
		String startTime = date+"T"+"00:00";
		LocalDateTime scheduleStartTime = LocalDateTime.parse(startTime);
		List<Meeting> resultMeetings = meetings.stream()
									.filter(m -> m.getOrganizer().equals(emp))
									.filter(m -> m.getStartTime().isAfter(scheduleStartTime))
									.collect(Collectors.toList());
		return resultMeetings;
	}

	public HashSet<MeetingRoom> getAllMeetingRooms() {
		return meetingRooms;
	}
}
