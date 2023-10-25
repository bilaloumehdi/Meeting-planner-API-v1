package com.bilal.meetingplanner.repository;

import com.bilal.meetingplanner.entity.MeetingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MeetingTypeRepository extends JpaRepository<MeetingType,Long> {

    Optional<MeetingType> findByName(String name);
}
