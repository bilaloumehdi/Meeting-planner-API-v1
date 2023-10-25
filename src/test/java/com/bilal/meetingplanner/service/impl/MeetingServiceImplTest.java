package com.bilal.meetingplanner.service.impl;

import com.bilal.meetingplanner.dto.MeetingRequest;
import com.bilal.meetingplanner.entity.Equipment;
import com.bilal.meetingplanner.entity.MeetingRoom;
import com.bilal.meetingplanner.entity.MeetingType;
import com.bilal.meetingplanner.exception.InvalidTimeRangeException;
import com.bilal.meetingplanner.exception.MeetingRoomNotFoundException;
import com.bilal.meetingplanner.repository.MeetingRepository;
import com.bilal.meetingplanner.service.MeetingRoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MeetingServiceImplTest {

    @Mock
    private MeetingRepository meetingRepository;

    @Mock
    private MeetingRoomService meetingRoomService;

    @InjectMocks
    private MeetingServiceImpl meetingService;


    @BeforeEach
    void setUp() {
    }


    @Test
    void meetingService_bookMeeting_throwInvalidTimeRangeException() {

        var startTime = LocalDateTime.of(2023,10,25,22,0,0);
        var endTime =  LocalDateTime.of(2023,10,25,23,0,0);

        var meetingRequest = new MeetingRequest("meeting-test",startTime,endTime,10,"VC");

        assertThatThrownBy(() -> meetingService.bookMeeting(meetingRequest))
                .isExactlyInstanceOf(InvalidTimeRangeException.class)
                .hasMessage("the date format that you provided is invalid");
    }
    @Test
    void meetingService_bookMeeting_throwInvalidTimeRangeException_Weekend() {
        // day of month = 28 --> saturday
        var startTime = LocalDateTime.of(2023,10,28,8,0,0);
        var endTime =  LocalDateTime.of(2023,10,28,9,0,0);

        var meetingRequest = new MeetingRequest("meeting-test",startTime,endTime,10,"VC");

        assertThatThrownBy(() -> meetingService.bookMeeting(meetingRequest))
                .isExactlyInstanceOf(InvalidTimeRangeException.class)
                .hasMessage("the date format that you provided is invalid");
    }

    @Test
    void meetingService_bookMeeting_throwInvalidTimeRangeException_DurationMoreThanOneHour() {
        var startTime = LocalDateTime.of(2023,10,28,8,0,0);
        var endTime =  LocalDateTime.of(2023,10,28,10,0,0);

        var meetingRequest = new MeetingRequest("meeting-test",startTime,endTime,10,"VC");

        assertThatThrownBy(() -> meetingService.bookMeeting(meetingRequest))
                .isExactlyInstanceOf(InvalidTimeRangeException.class)
                .hasMessage("the date format that you provided is invalid");
    }
    @Test
    void meetingService_bookMeeting_throwInvalidTimeRangeException_HourFormatNotValid() {

        var startTime = LocalDateTime.of(2023,10,28,8,10,0);
        var endTime =  LocalDateTime.of(2023,10,28,9,10,0);

        var meetingRequest = new MeetingRequest("meeting-test",startTime,endTime,10,"VC");

        assertThatThrownBy(() -> meetingService.bookMeeting(meetingRequest))
                .isExactlyInstanceOf(InvalidTimeRangeException.class)
                .hasMessage("the date format that you provided is invalid");
    }

    @Test
    void meetingService_bookMeeting_throwMeetingRoomNotFoundException() {

        var startTime = LocalDateTime.of(2023,10,26,8,0,0);
        var endTime =  LocalDateTime.of(2023,10,26,9,0,0);

        var meetingRequest = new MeetingRequest("meeting-test",startTime,endTime,10,"VC");

        when(meetingRoomService.getAvailableRoomsAtSpecificTime(startTime,endTime))
                .thenReturn(List.of());

        assertThatThrownBy(() -> meetingService.bookMeeting(meetingRequest))
                .isExactlyInstanceOf(MeetingRoomNotFoundException.class)
                .hasMessage("there is no meeting rooms available at the time specified");
    }



    @Test
    void meetingService_filterAvailableRoomsByCapacity_returnOnly2Suitable() {
        var availableRooms = List.of(
                new MeetingRoom(null,"E1001",23, null),
                new MeetingRoom(null,"E1001",10, null),
                new MeetingRoom(null,"E1001",6, null),
                new MeetingRoom(null,"E1001",3, null)
        );

        assertThat(meetingService.filterAvailableRoomsByCapacity(availableRooms,7).size())
                .isEqualTo(2);

    }

    @Test
    void meetingService_getMeetingRoomThatSupportMeetingType_returnOnly2Suitable() {
        Equipment ecran = new Equipment(null,"Ecran");
        Equipment pieuvre = new Equipment(null,"Pieuvre");
        Equipment tableau =  new Equipment(null,"Tableau");
        Equipment webcam = new Equipment(null,"Webcam");

        var availableRooms = List.of(
                new MeetingRoom(null,"E1001",23, Set.of(ecran,pieuvre)),
                new MeetingRoom(null,"E1002",10, Set.of(tableau)),
                new MeetingRoom(null,"E1003",6, Set.of()),
                new MeetingRoom(null,"E1004",3, Set.of(webcam,ecran,pieuvre))
        );

        var meetingType = MeetingType.builder()
                .name("VC")
                .requiredEquipments(Set.of(webcam,ecran,pieuvre))
                .build();

        assertThat(meetingService.getMeetingRoomThatSupportMeetingType(availableRooms,meetingType).get().getName())
                .isEqualTo("E1004");

    }


}