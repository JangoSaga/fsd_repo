
package com.conference.participants.Entity;

import org.springframework.stereotype.Service;

import jakarta.persistence.*;
import org.springframework.transaction.annotation.Transactional;

import com.conference.participants.Repository.ParticipantRepository;

import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "participants")
public class Participant {

    @Id
    @Column(name = "participants_id")
    private String participantsId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email_id", nullable = false, unique = true)
    private String emailId;

    @Column(name = "paper_title")
    private String paperTitle;

    @Column(name = "abstract", columnDefinition = "TEXT")
    private String abstract_;

    @Column(name = "acceptance_status")
    private String acceptanceStatus;

    @Column(name = "track")
    private String track;

    @Column(name = "co_authors")
    private String coAuthors;

    public String getParticipantsId() {
        return participantsId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getPaperTitle() {
        return paperTitle;
    }

    public String getAbstract_() {
        return abstract_;
    }

    public String getAcceptanceStatus() {
        return acceptanceStatus;
    }

    public String getTrack() {
        return track;
    }

    public String getCoAuthors() {
        return coAuthors;
    }

    // Setters
    public void setParticipantsId(String participantsId) {
        this.participantsId = participantsId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
    }

    public void setAbstract_(String abstract_) {
        this.abstract_ = abstract_;
    }

    public void setAcceptanceStatus(String acceptanceStatus) {
        this.acceptanceStatus = acceptanceStatus;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public void setCoAuthors(String coAuthors) {
        this.coAuthors = coAuthors;
    }

    // toString method for better object representation
    @Override
    public String toString() {
        return "Participant{" +
                "participantsId='" + participantsId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", emailId='" + emailId + '\'' +
                ", paperTitle='" + paperTitle + '\'' +
                ", abstract_='" + abstract_ + '\'' +
                ", acceptanceStatus='" + acceptanceStatus + '\'' +
                ", track='" + track + '\'' +
                ", coAuthors='" + coAuthors + '\'' +
                '}';
    }

    @Service
    @Transactional
    public static class ParticipantService {

        private final ParticipantRepository participantRepository;

        public ParticipantService(ParticipantRepository participantRepository) {
            this.participantRepository = participantRepository;
        }

        public Participant save(Participant participant) {
            validateParticipant(participant);
            return participantRepository.save(participant);
        }

        public Optional<Participant> findById(String id) {
            return participantRepository.findById(id);
        }

        public List<Participant> findAll() {
            return participantRepository.findAll();
        }

        public void deleteById(String id) {
            participantRepository.deleteById(id);
        }

        public List<Participant> findByTrack(String track) {
            return participantRepository.findByTrack(track);
        }

        public List<Participant> findByAcceptanceStatus(String status) {
            return participantRepository.findByAcceptanceStatus(status);
        }

        // Custom validation method
        private void validateParticipant(Participant participant) {
            if (participant.getFullName() == null || participant.getFullName().trim().isEmpty()) {
                throw new IllegalArgumentException("Full name cannot be empty");
            }
            if (participant.getEmailId() == null || participant.getEmailId().trim().isEmpty()) {
                throw new IllegalArgumentException("Email ID cannot be empty");
            }
            if (!participant.getEmailId().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                throw new IllegalArgumentException("Invalid email format");
            }
        }

        // Method to update participant status
        public Participant updateAcceptanceStatus(String participantId, String newStatus) {
            return participantRepository.findById(participantId)
                    .map(participant -> {
                        participant.setAcceptanceStatus(newStatus);
                        return participantRepository.save(participant);
                    })
                    .orElseThrow(() -> new RuntimeException("Participant not found"));
        }

        // Method to get participants by paper title (partial match)
        public List<Participant> searchByPaperTitle(String paperTitle) {
            return participantRepository.findByPaperTitleContainingIgnoreCase(paperTitle);
        }
    }
}
