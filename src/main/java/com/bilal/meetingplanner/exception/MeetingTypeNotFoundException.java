package com.bilal.meetingplanner.exception;

public class MeetingTypeNotFoundException extends RuntimeException {
    public MeetingTypeNotFoundException(String message) {
        super(message);
    }

    public MeetingTypeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
