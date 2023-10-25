package com.bilal.meetingplanner.service.impl;

import com.bilal.meetingplanner.entity.Equipment;
import com.bilal.meetingplanner.entity.MeetingRoom;
import com.bilal.meetingplanner.repository.MeetingRoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MeetingRoomServiceImplTest {
    @Mock
    private MeetingRoomRepository meetingRoomRepository;
    @InjectMocks
    private MeetingRoomServiceImpl meetingRoomService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void meetingRoomServices_AvailableRoomsAtSpecificTime_returnAvailableRoom(){

        MeetingRoom meetingRoom = MeetingRoom.builder()
                .id(2930L)
                .name("E8472")
                .capacity(20)
                .availableEquipments(Set.of(new Equipment(null,"Ecran"),new Equipment(null,"Webcam")))
                .build();

        var startTime = LocalDateTime.of(2023,10,25,11,0,0);
        var endTime =  LocalDateTime.of(2023,10,25,12,0,0);

        when(meetingRoomRepository
                .findAvailableRoomsAtSpecificTime(startTime,endTime,startTime.minusHours(1),endTime.plusHours(1)))
                .thenReturn(List.of(meetingRoom));

        var availableRooms = meetingRoomService.getAvailableRoomsAtSpecificTime(startTime,endTime);

        assertThat(availableRooms).isNotEmpty();
        assertThat(availableRooms.size()).isEqualTo(1);

    }


}