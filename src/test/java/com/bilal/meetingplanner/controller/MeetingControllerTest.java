package com.bilal.meetingplanner.controller;

import com.bilal.meetingplanner.dto.MeetingRequest;
import com.bilal.meetingplanner.entity.Meeting;
import com.bilal.meetingplanner.entity.MeetingRoom;
import com.bilal.meetingplanner.entity.MeetingType;
import com.bilal.meetingplanner.service.MeetingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
@ContextConfiguration(classes = MeetingController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class MeetingControllerTest {
    @Autowired
    private  MockMvc mockMvc;

    @MockBean
    private MeetingService meetingService;

    @Autowired
    private ObjectMapper objectMapper;

    private MeetingType meetingType;
    private MeetingRoom meetingRoom;
    private Meeting meeting;

    @BeforeEach
    void setUp() {
        var startTime = LocalDateTime.of(2023,10,28,8,0,0);
        var endTime =  LocalDateTime.of(2023,10,28,10,0,0);

        meetingRoom = MeetingRoom.builder()
                .name("E2839")
                .id(20L)
                .capacity(15)
                .build();

        meetingType = MeetingType.builder()
                .name("meeting-test")
                .id(10L)
                .build();

         meeting = Meeting.builder()
                .meetingName("RS")
                .meetingRoom(meetingRoom)
                .meetingType(meetingType)
                .startTime(startTime)
                 .endTime(endTime)
                .build();
    }

    @Test
    public void meetingController_whenRequestMeeting_returnMeetingAvailable() throws Exception {

        var startTime = LocalDateTime.of(2023,10,28,8,0,00);
        var endTime =  LocalDateTime.of(2023,10,28,10,0,00);

        var meetingRequest = new MeetingRequest("test",startTime,endTime,10,"VC");

       when(meetingService.bookMeeting(meetingRequest))
               .thenReturn(meeting);

        mockMvc.perform(post("/api/v1/meeting/meeting-request")
                      .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(meetingRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.meetingName", CoreMatchers.is(meeting.getMeetingName())))
                .andExpect(jsonPath("$.data.meetingType.name", CoreMatchers.is(meeting.getMeetingType().getName())));
    }


    @Test
    public void meetingController_whenRequestMeeting_throwBadRequestException() throws Exception {

        var endTime =  LocalDateTime.of(2023,10,28,10,0,00);

        var meetingRequest = new MeetingRequest("test",null,endTime,10,"VC");

        when(meetingService.bookMeeting(meetingRequest))
                .thenReturn(meeting);

        mockMvc.perform(post("/api/v1/meeting/meeting-request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(meetingRequest)))
                .andExpect(status().isBadRequest());
    }


}