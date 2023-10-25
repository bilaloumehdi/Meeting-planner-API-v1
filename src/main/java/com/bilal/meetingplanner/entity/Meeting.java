package com.bilal.meetingplanner.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime startTime;
    @Column(nullable = false)
    private LocalDateTime endTime;

    private String meetingName;


    @ManyToOne
    @JoinColumn(
            name = "meeting_type_id",
            referencedColumnName = "id"
    )
    private MeetingType meetingType;


    @ManyToOne()
    @JoinColumn(
            name = "meeting_room_id",
            referencedColumnName = "id"
    )
    private MeetingRoom meetingRoom;

}
