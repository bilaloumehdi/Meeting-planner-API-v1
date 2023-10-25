package com.bilal.meetingplanner.repository;

import com.bilal.meetingplanner.entity.Meeting;
import com.bilal.meetingplanner.entity.MeetingRoom;
import com.bilal.meetingplanner.entity.MeetingType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@DataJpaTest
class MeetingRepositoryTest {

    @Autowired
    private MeetingRepository meetingRepository;

    @MockBean
    private MeetingType meetingType;
    @MockBean
    private MeetingRoom meetingRoom;

    @BeforeEach
    void setUp() {
        meetingRoom = new MeetingRoom(1L,"E1001",10, Set.of());
        meetingType = new MeetingType(1L,"VC",Set.of());
    }

    @Test
    public void meetingRepository_save_returnSavedMeeting(){

        //Arrange
        var startTime = LocalDateTime.of(2023,10,3,8,0,0);
        var endTime = LocalDateTime.of(2023,10,3,9,0,0);

        Meeting meeting = Meeting.builder()
                .meetingName("sprint-m")
                .meetingRoom(meetingRoom)
                .meetingType(meetingType)
                .startTime(startTime)
                .endTime(endTime)
                .build();
        // Act
        Meeting savedMeeting = meetingRepository.save(meeting);

        // Assert
        //Assertions.assertThat(savedMeeting).isNotNull();
        Assertions.assertThat(savedMeeting.getMeetingType().getName()).isEqualTo("VC");
    }

    @Test
    @DisplayName("findByMeetingRoomAndStartTime test")
    public void meetingRepository_findByMeetingRoomAndStartTime_returnMeeting(){

        var startTime = LocalDateTime.of(2023,10,3,8,0,0);
        var endTime =  LocalDateTime.of(2023,10,3,9,0,0);

        Meeting meeting = Meeting.builder()
                .meetingName("sprint-m")
                .meetingRoom(meetingRoom)
                .meetingType(meetingType)
                .startTime(startTime)
                .endTime(endTime)
                .build();
        meetingRepository.save(meeting);

        Optional<Meeting> foundedMeeting = meetingRepository.findByMeetingRoomAndStartTime(meetingRoom,startTime);

        Assertions.assertThat(foundedMeeting.get().getStartTime())
                .isEqualTo(startTime);

    }
}