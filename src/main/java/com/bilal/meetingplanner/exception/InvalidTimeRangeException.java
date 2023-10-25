package com.bilal.meetingplanner.exception;

public class InvalidTimeRangeException extends RuntimeException {

    public InvalidTimeRangeException(String message) {
        super(message);
    }

    public InvalidTimeRangeException(String message, Throwable cause) {
        super(message, cause);
    }
}
