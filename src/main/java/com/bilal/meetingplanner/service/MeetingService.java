package com.bilal.meetingplanner.service;

import com.bilal.meetingplanner.dto.MeetingRequest;
import com.bilal.meetingplanner.entity.Meeting;
import org.springframework.stereotype.Service;


public interface MeetingService {
    Meeting bookMeeting(MeetingRequest meetingRequest);

}
