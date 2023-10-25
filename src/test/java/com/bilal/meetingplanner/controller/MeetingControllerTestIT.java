package com.bilal.meetingplanner.controller;

import com.bilal.meetingplanner.dto.MeetingRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ContextConfiguration(classes = MeetingController.class)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class MeetingControllerTestIT {

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    public MeetingControllerTestIT(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void meetingControllerIT_bookMeeting_returnBookedMeeting() throws Exception {

        var startTime = LocalDateTime.of(2023,10,28,8,0,00);
        var endTime =  LocalDateTime.of(2023,10,28,10,0,00);

        var meetingRequest = new MeetingRequest("test",startTime,endTime,10,"VC");

        mockMvc.perform(post("/api/v1/meeting/meeting-request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(meetingRequest)))
                        .andExpect(status().isOk());
    }


}
