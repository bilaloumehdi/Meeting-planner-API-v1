package com.bilal.meetingplanner.controller;

import com.bilal.meetingplanner.dto.MeetingRequest;
import com.bilal.meetingplanner.dto.Response;
import com.bilal.meetingplanner.service.MeetingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/meeting")
public class MeetingController {

    private final MeetingService meetingService;

    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @PostMapping("/meeting-request")
    public ResponseEntity<Response> requestMeeting(@RequestBody @Validated MeetingRequest meetingRequest){
        var meeting = meetingService.bookMeeting(meetingRequest);

        return ResponseEntity.ok(
                new Response(
                        HttpStatus.OK.value(),
                        HttpStatus.OK, meeting

                )
        );
    }



}
