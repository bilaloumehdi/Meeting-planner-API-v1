package com.bilal.meetingplanner.repository;

import com.bilal.meetingplanner.entity.Equipment;
import com.bilal.meetingplanner.entity.Meeting;
import com.bilal.meetingplanner.entity.MeetingRoom;
import com.bilal.meetingplanner.entity.MeetingType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@DataJpaTest
class MeetingRoomRepositoryTest {
    @Autowired
    private MeetingRoomRepository meetingRoomRepository;
    @Autowired
    private MeetingRepository meetingRepository;
    @MockBean
    private MeetingType meetingType;

    @BeforeEach
    void setUp() {
            Equipment ecran = new Equipment(null,"Ecran");
            Equipment pieuvre = new Equipment(null,"Pieuvre");

        meetingType = new MeetingType(1L,"VC",Set.of(ecran,pieuvre));

    }

    @Test
    public void meetingRoomRepository_findAvailableRoomsAtSpecificTime_ReturnMeetingRoomsNotContainsRoom2(){



        var startTime = LocalDateTime.of(2023,10,24,9,0,0);
        var endTime =  LocalDateTime.of(2023,10,24,10,0,0);

        //reserve the meeting room number 2
        var meeting = Meeting.builder()
                .meetingType(meetingType)
                .meetingRoom(meetingRoomRepository.findById(2L).get())
                .meetingName("test-1")
                .startTime(startTime)
                .endTime(endTime)
                .build();

        meetingRepository.save(meeting);

        var availableMeetingRooms = meetingRoomRepository.findAvailableRoomsAtSpecificTime(startTime,endTime,startTime.minusHours(1),endTime.plusHours(1));

        Assertions.assertThat(availableMeetingRooms.get(1).getId()).isEqualTo(3);
    }

    @Test
    public void meetingRoomRepository_findAvailableRoomsAtSpecificTime_ReturnMeetingRoomsNotContaining2And3(){

        var startTime = LocalDateTime.of(2023,10,24,9,0,0);
        var endTime =  LocalDateTime.of(2023,10,24,10,0,0);

        //reserve the meeting room number 2
        var meeting = Meeting.builder()
                .meetingType(meetingType)
                .meetingRoom(meetingRoomRepository.findById(2L).get())
                .meetingName("test-1")
                .startTime(startTime)
                .endTime(endTime)
                .build();

        //reserve the meeting room number 3
        var meeting2 = Meeting.builder()
                .meetingType(meetingType)
                .meetingRoom(meetingRoomRepository.findById(3L).get())
                .meetingName("test-1")
                .startTime(startTime)
                .endTime(endTime)
                .build();

        meetingRepository.save(meeting);
        meetingRepository.save(meeting2);


        var availableMeetingRooms = meetingRoomRepository.findAvailableRoomsAtSpecificTime(startTime,endTime,startTime.minusHours(1),endTime.plusHours(1));

        Assertions.assertThat(availableMeetingRooms.get(1).getId()).isEqualTo(4);
    }

}