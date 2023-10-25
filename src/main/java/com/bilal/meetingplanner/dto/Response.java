package com.bilal.meetingplanner.dto;

import com.bilal.meetingplanner.entity.Meeting;
import org.springframework.http.HttpStatus;

import java.util.Map;

public record Response(
        int statusCode,
        HttpStatus status,
        Meeting data
) { }
