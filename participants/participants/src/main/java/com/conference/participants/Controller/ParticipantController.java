
package com.conference.participants.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.conference.participants.Entity.Participant;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/participants")
public class ParticipantController {

    @Autowired
    private Participant.ParticipantService participantService;

    @PostMapping
    public ResponseEntity<Participant> createParticipant(@RequestBody Participant participant) {
        try {
            return ResponseEntity.ok(participantService.save(participant));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Participant> getParticipantById(@PathVariable String id) {
        return participantService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Participant>> getAllParticipants() {
        return ResponseEntity.ok(participantService.findAll());
    }

    @GetMapping("/track/{track}")
    public ResponseEntity<List<Participant>> getParticipantsByTrack(@PathVariable String track) {
        return ResponseEntity.ok(participantService.findByTrack(track));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Participant>> getParticipantsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(participantService.findByAcceptanceStatus(status));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Participant>> searchByPaperTitle(@RequestParam String title) {
        return ResponseEntity.ok(participantService.searchByPaperTitle(title));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Participant> updateParticipant(
            @PathVariable String id,
            @RequestBody Participant participant) {
        try {
            return participantService.findById(id)
                    .map(existingParticipant -> {
                        participant.setParticipantsId(id);
                        return ResponseEntity.ok(participantService.save(participant));
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Participant> updateStatus(
            @PathVariable String id,
            @RequestParam String status) {
        try {
            return ResponseEntity.ok(participantService.updateAcceptanceStatus(id, status));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable String id) {
        return participantService.findById(id)
                .map(participant -> {
                    participantService.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}