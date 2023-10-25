package com.bilal.meetingplanner.service;

import com.bilal.meetingplanner.entity.MeetingRoom;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
public interface MeetingRoomService {

    List<MeetingRoom> getAvailableRoomsAtSpecificTime(LocalDateTime startTime, LocalDateTime endTime);


}
