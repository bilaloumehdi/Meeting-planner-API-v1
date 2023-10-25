package com.bilal.meetingplanner.service.impl;

import com.bilal.meetingplanner.dto.MeetingRequest;
import com.bilal.meetingplanner.entity.Meeting;
import com.bilal.meetingplanner.service.MeetingService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

@SpringBootTest
@ExtendWith({SpringExtension.class})
public class MeetingServiceTestIT {

    private final MeetingServiceImpl meetingService;

    @Autowired
    public MeetingServiceTestIT(MeetingServiceImpl meetingService) {
        this.meetingService = meetingService;
    }

    @Test
    public void meetingServiceIT_bookMeeting_returnBookedMeeting(){

        var meetingRequest = new MeetingRequest(
                "meet-1",
                LocalDateTime.of(2023,10,26,8,0,0),
                LocalDateTime.of(2023,10,26,9,0,0),
                9,
                "SPEC"
                );

        Assertions.assertThat(meetingService.bookMeeting(meetingRequest))
                .isExactlyInstanceOf(Meeting.class);
    }
}
