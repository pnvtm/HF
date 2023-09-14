package com.pnvtm.hfs.cr.controller;

import com.pnvtm.hfs.cr.domain.EventData;
import com.pnvtm.hfs.cr.service.impl.EventServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventStatisticsController {

    @Autowired
    private EventServiceImpl eventService;

    @PostMapping("/event")
    @ResponseStatus(value=HttpStatus.ACCEPTED)
    public void addEvent(EventData eventData) {
        if (null == eventData){
            throw new IllegalArgumentException();
        }
        eventService.placeEvent(eventData);
    }

    @GetMapping("/stats")
    public ResponseEntity<String> getStats() {

        String result = eventService.getStat();

        return ResponseEntity.ok(result);
    }
}