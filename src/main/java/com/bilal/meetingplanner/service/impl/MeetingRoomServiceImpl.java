package com.bilal.meetingplanner.service.impl;

import com.bilal.meetingplanner.entity.MeetingRoom;
import com.bilal.meetingplanner.repository.MeetingRoomRepository;
import com.bilal.meetingplanner.service.MeetingRoomService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class MeetingRoomServiceImpl implements MeetingRoomService {
    private final MeetingRoomRepository meetingRoomRepository;

    public MeetingRoomServiceImpl(MeetingRoomRepository meetingRoomRepository) {
        this.meetingRoomRepository = meetingRoomRepository;
    }

    @Override
    public List<MeetingRoom> getAvailableRoomsAtSpecificTime(LocalDateTime startTime, LocalDateTime endTime){

        return meetingRoomRepository.findAvailableRoomsAtSpecificTime(
                startTime, endTime, startTime.minusHours(1), endTime.plusHours(1)
        );
    }
}
