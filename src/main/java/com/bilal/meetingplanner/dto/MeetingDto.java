package com.bilal.meetingplanner.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record MeetingDto(

        LocalDateTime startTime,
        LocalDateTime endTime,
        String meetingName,
        String meetingRoomName
        ) {
}
