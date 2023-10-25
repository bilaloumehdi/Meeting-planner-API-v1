package com.bilal.meetingplanner.repository;

import com.bilal.meetingplanner.entity.Meeting;
import com.bilal.meetingplanner.entity.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting,Long> {

    Optional<Meeting> findByMeetingRoomAndStartTime(MeetingRoom meetingRoom, LocalDateTime startTime);
}
