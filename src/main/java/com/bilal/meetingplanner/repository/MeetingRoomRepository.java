package com.bilal.meetingplanner.repository;

import com.bilal.meetingplanner.entity.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MeetingRoomRepository extends JpaRepository<MeetingRoom,Long> {

    @Query("SELECT room " +
            "FROM MeetingRoom room " +
            "WHERE room NOT IN (SELECT m.meetingRoom FROM Meeting m " +
            "                   WHERE (m.endTime > :startTime AND m.startTime < :endTime)) " +
            "AND room NOT IN (SELECT m.meetingRoom FROM Meeting m " +
            "                WHERE (m.endTime > :startTimeMinusOneHour AND m.startTime < :startTime) " +
            "                OR (m.startTime < :endTimePlusOneHour AND m.endTime > :endTime))")
    List<MeetingRoom> findAvailableRoomsAtSpecificTime(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("startTimeMinusOneHour") LocalDateTime startTimeMinusOneHour,
            @Param("endTimePlusOneHour") LocalDateTime endTimePlusOneHour
    );



}
