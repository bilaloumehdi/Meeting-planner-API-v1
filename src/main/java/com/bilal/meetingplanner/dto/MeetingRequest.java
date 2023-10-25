package com.bilal.meetingplanner.dto;

import com.bilal.meetingplanner.entity.MeetingType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record MeetingRequest(
        String name,
        @NotNull(message = "you need to provide the start time")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm[:ss]")
        LocalDateTime startTime,
        @NotNull(message = "you need to provide the end time")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm[:ss]")
        LocalDateTime endTime,
        @NotNull(message = "you need to provide the number of participants")
        int participantsNumber,
        @NotBlank(message = "meeting type name could not be empty")
        @NotNull(message = "you need to provide the type of meeting")
        String meetingTypeName
        ) { }
