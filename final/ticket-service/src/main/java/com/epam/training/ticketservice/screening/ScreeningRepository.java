package com.epam.training.ticketservice.screening;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening,Long> {

    Optional<Screening> findByFilmNameAndRoomNameAndStart(String filmName, String roomName, LocalDateTime start);
}
