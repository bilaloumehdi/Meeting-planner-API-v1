package com.bilal.meetingplanner.service;

import com.bilal.meetingplanner.entity.MeetingType;
import org.springframework.stereotype.Service;

public interface MeetingTypeService {

    MeetingType getMeetingTypeByName(String name);
}
