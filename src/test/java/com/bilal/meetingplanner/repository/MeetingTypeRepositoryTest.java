package com.bilal.meetingplanner.repository;

import com.bilal.meetingplanner.entity.Equipment;
import com.bilal.meetingplanner.entity.MeetingType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class MeetingTypeRepositoryTest {
    @Autowired
    private MeetingTypeRepository meetingTypeRepository;
    @BeforeEach
    void setUp() {
    }

    @Test
    public void meetingTypeRepository_findByName_ReturnMeetingType(){
        MeetingType meetingType = MeetingType.builder()
                .name("SimpleTest")
                .requiredEquipments(Set.of(new Equipment(1L,"ecran")))
                .build();

        meetingTypeRepository.save(meetingType);

        MeetingType foundedMeetingType = meetingTypeRepository.findByName(meetingType.getName()).get();

        assertThat(foundedMeetingType).isNotNull();
        assertThat(foundedMeetingType.getName()).isEqualTo(meetingType.getName());
    }

    @Test
    public void meetingTypeRepository_findByName_ReturnEmpty(){

        Optional<MeetingType> foundedMeetingType = meetingTypeRepository.findByName("Test");

        assertThat(foundedMeetingType).isNotNull();
        assertThat(foundedMeetingType).isEmpty();
    }

}