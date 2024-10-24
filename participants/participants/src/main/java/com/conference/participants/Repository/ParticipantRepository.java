
package com.conference.participants.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.conference.participants.Entity.Participant;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, String> {
    List<Participant> findByTrack(String track);

    List<Participant> findByAcceptanceStatus(String status);

    List<Participant> findByPaperTitleContainingIgnoreCase(String paperTitle);
}