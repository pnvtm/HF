package com.pnvtm.hfs.cr.controller;

import com.pnvtm.hfs.cr.service.impl.EventServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

@RestController
@RequestMapping("/api")
public class EventStatisticsController {

    @Autowired
    private EventServiceImpl eventService;

    @PostMapping("/event")
    public ResponseEntity<Void> addEvent(@RequestBody String eventData) {
        if(eventData.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        try {
            String[] parts = eventData.split(",");
            if (parts.length != 3) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            long timestamp = Long.parseLong(parts[0]);
            double dataX = Double.parseDouble(parts[1]);
            int dataY = Integer.parseInt(parts[2]);

            eventService.placeEvent(timestamp, dataX, dataY);

            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<String> getStats() {
        try {
            String result = eventService.getStat();

            return ResponseEntity.ok(result);

        } catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
