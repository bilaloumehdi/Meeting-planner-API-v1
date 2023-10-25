package com.bilal.meetingplanner.dto;

import org.springframework.http.HttpStatus;

public record ErrorResponse(
        int statusCode,
        HttpStatus status,
        String message
) {}
