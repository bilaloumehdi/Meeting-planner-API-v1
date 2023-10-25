package com.bilal.meetingplanner.service.impl;

import com.bilal.meetingplanner.dto.MeetingRequest;
import com.bilal.meetingplanner.entity.Meeting;
import com.bilal.meetingplanner.entity.MeetingRoom;
import com.bilal.meetingplanner.entity.MeetingType;
import com.bilal.meetingplanner.exception.InvalidTimeRangeException;
import com.bilal.meetingplanner.exception.MeetingRoomNotFoundException;
import com.bilal.meetingplanner.repository.MeetingRepository;
import com.bilal.meetingplanner.service.MeetingService;
import com.bilal.meetingplanner.service.MeetingTypeService;
import com.bilal.meetingplanner.service.MeetingRoomService;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepository;

    private final MeetingRoomService meetingRoomService;

    private final MeetingTypeService meetingTypeService;

    public MeetingServiceImpl(
            MeetingRepository meetingRepository,
            MeetingRoomService meetingRoomService
            ,MeetingTypeService meetingTypeService) {
        this.meetingRepository = meetingRepository;
        this.meetingRoomService = meetingRoomService;
        this.meetingTypeService = meetingTypeService;
    }
    @Override
    public Meeting bookMeeting(MeetingRequest meetingRequest){

        if(!isValidTimeRange(meetingRequest.startTime(), meetingRequest.endTime()))
            throw  new InvalidTimeRangeException("the date format that you provided is invalid");

        var availableRooms = meetingRoomService.getAvailableRoomsAtSpecificTime(meetingRequest.startTime(),meetingRequest.endTime());

        //check that there is an available rooms at this time
        if(availableRooms == null || availableRooms.isEmpty())
            throw new MeetingRoomNotFoundException("there is no meeting rooms available at the time specified");

        var participantsNumber =  meetingRequest.participantsNumber();
        var meetingRoomsThatSupportParticipantsNumber = filterAvailableRoomsByCapacity(availableRooms,participantsNumber );

        if(meetingRoomsThatSupportParticipantsNumber ==null || meetingRoomsThatSupportParticipantsNumber.isEmpty())
            throw new MeetingRoomNotFoundException("there is no meeting room available that support [%s] participants".formatted(participantsNumber));

        // check for equipments between meeting rooms and required equipments for the meetingType
        var meetingType = meetingTypeService.getMeetingTypeByName(meetingRequest.meetingTypeName());

        var meetingRoom = getMeetingRoomThatSupportMeetingType(meetingRoomsThatSupportParticipantsNumber,meetingType)
                .orElseThrow(() -> new MeetingRoomNotFoundException("no available room support the meeting type"));

        return meetingRepository.save(
                Meeting.builder()
                        .meetingType(meetingType)
                        .meetingRoom(meetingRoom)
                        .startTime(meetingRequest.startTime())
                        .endTime(meetingRequest.endTime())
                        .meetingName(meetingRequest.meetingTypeName())
                        .build()
        );
    }



    private boolean isValidTimeRange(LocalDateTime start, LocalDateTime end){

        if(start == null || end == null) return false;

        // check that the duration is 1hour
        if(!Duration.between(start,end).equals(Duration.ofHours(1))) return false;

        //check the format of data (exp 8h00 - 9h00)
        if(start.getMinute() != 0 || end.getMinute() != 0) return false;

        // check that is in the interval of 8H-20H
        if(start.getHour() < 8 || start.getHour() >= 20 || end.getHour() >= 20) return false;

        //check that the meeting is not in the weekend
        if(start.getDayOfWeek() == DayOfWeek.SATURDAY || start.getDayOfWeek() == DayOfWeek.SUNDAY) return false;

        return true;
    }
    public List<MeetingRoom> filterAvailableRoomsByCapacity(List<MeetingRoom> availableRooms, int participantsNumber ){
        return availableRooms.stream()
                .filter(room -> (int)(room.getCapacity() * 0.7) >= participantsNumber)
                .sorted(Comparator.comparingInt(MeetingRoom::getCapacity))
                .collect(Collectors.toList());
    }

    public Optional<MeetingRoom> getMeetingRoomThatSupportMeetingType(List<MeetingRoom> meetingRooms, MeetingType meetingType) {

        // if the meeting type does not require any equipments
        if(meetingType.getRequiredEquipments().isEmpty()){
            return Optional.ofNullable(meetingRooms.get(meetingRooms.size() -1));
        }

        for(var meetingRoom: meetingRooms){
            if(meetingRoom.getAvailableEquipments().containsAll(meetingType.getRequiredEquipments())){
                return Optional.ofNullable(meetingRoom);
            }
        }
        return Optional.empty();
    }

}


