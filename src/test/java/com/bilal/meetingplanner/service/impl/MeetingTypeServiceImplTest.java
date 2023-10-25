package com.bilal.meetingplanner.service.impl;

import com.bilal.meetingplanner.entity.MeetingType;
import com.bilal.meetingplanner.exception.MeetingTypeNotFoundException;
import com.bilal.meetingplanner.repository.MeetingTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MeetingTypeServiceImplTest {
    @Mock
    private MeetingTypeRepository meetingTypeRepository;
    @InjectMocks
    private MeetingTypeServiceImpl meetingTypeService;


    @Test
    public void meetingTypeService_getMeetingTypeByName_returnMeetingTypeVC(){

        var meetingTypeName = "VC";
        var meetingType = MeetingType.builder()
                .id(1L)
                .name(meetingTypeName)
                .build();
        when(meetingTypeRepository.findByName(meetingTypeName))
                .thenReturn(Optional.of(meetingType));

        assertThat(meetingTypeService.getMeetingTypeByName(meetingTypeName).getName())
                .isEqualTo(meetingTypeName);

    }

    @Test
    public void meetingTypeService_getMeetingTypeByName_throwsMeetingTypeNotFoundException(){

        var meetingTypeName = "VC";
        var meetingType = MeetingType.builder()
                .id(1L)
                .name(meetingTypeName)
                .build();
        when(meetingTypeRepository.findByName(meetingTypeName))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->meetingTypeService.getMeetingTypeByName(meetingTypeName))
                .isExactlyInstanceOf(MeetingTypeNotFoundException.class);

    }

}