package com.bilal.meetingplanner.exception;

public class MeetingRoomNotFoundException extends RuntimeException {

    public MeetingRoomNotFoundException(String message) {
        super(message);
    }

    public MeetingRoomNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
