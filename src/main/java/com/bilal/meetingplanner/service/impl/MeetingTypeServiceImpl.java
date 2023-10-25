package com.bilal.meetingplanner.service.impl;

import com.bilal.meetingplanner.entity.MeetingType;
import com.bilal.meetingplanner.exception.MeetingTypeNotFoundException;
import com.bilal.meetingplanner.repository.MeetingTypeRepository;
import com.bilal.meetingplanner.service.MeetingTypeService;
import org.springframework.stereotype.Service;
@Service
public class MeetingTypeServiceImpl implements MeetingTypeService {

    private final MeetingTypeRepository meetingTypeRepository;


    public MeetingTypeServiceImpl(MeetingTypeRepository meetingTypeRepository) {
        this.meetingTypeRepository = meetingTypeRepository;
    }

    @Override
    public MeetingType getMeetingTypeByName(String name) {
        return meetingTypeRepository.findByName(name)
                .orElseThrow(() -> new MeetingTypeNotFoundException("could not found the meeting type"));
    }
}
