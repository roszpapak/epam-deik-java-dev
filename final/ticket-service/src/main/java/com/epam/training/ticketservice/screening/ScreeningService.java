package com.epam.training.ticketservice.screening;

import com.epam.training.ticketservice.movie.Movie;
import com.epam.training.ticketservice.movie.MovieRepository;
import com.epam.training.ticketservice.room.Room;
import com.epam.training.ticketservice.room.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
public class ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;


    public String createScreening(Screening screening) {
        if (movieRepository.existsById(screening.getFilmName()) && roomRepository.existsById(screening.getRoomName())) {

            List<Screening> screeningList = screeningRepository.findByRoomName(screening.getRoomName());
            if (isOverLapping(screeningList, screening)) {
                return ("There is an overlapping screening");
            } else if (isInBreak(screeningList, screening)) {
                return ("This would start in the break period after another screening in this room");
            } else {
                screeningRepository.save(screening);
                return "User succesfully added";
            }

        } else {
            throw new IllegalArgumentException();
        }

    }

    public boolean isInBreak(List<Screening> screeningList, Screening screening) {
        boolean isInBreak = false;
        LocalDateTime screeningStart = screening.getStart();

        for (var actual : screeningList) {
            int actualLength = movieRepository.findById(actual.getFilmName()).get().getLength();
            LocalDateTime actualEnd = actual.getStart().plusMinutes(actualLength);
            if (screeningStart.isAfter(actualEnd) && screeningStart.isBefore(actualEnd.plusMinutes(10))) {
                isInBreak = true;
            }

        }
        return isInBreak;

    }

    public boolean isOverLapping(List<Screening> screeningList, Screening screening) {
        boolean isOverLapping = false;
        int screeningLength = movieRepository.findById(screening.getFilmName()).get().getLength();
        LocalDateTime screeningStart = screening.getStart();
        LocalDateTime screeningEnd = screening.getStart().plusMinutes(screeningLength);
        for (var actual : screeningList) {
            int actualLength = movieRepository.findById(actual.getFilmName()).get().getLength();
            LocalDateTime actualStart = actual.getStart();
            LocalDateTime actualEnd = actual.getStart().plusMinutes(actualLength);

            if (screeningEnd.isAfter(actualStart) && screeningEnd.isBefore(actualEnd)
                    ||
                    screeningStart.isAfter(actualStart) && screeningEnd.isBefore(actualEnd)
                    ||
                    screeningStart.isAfter(actualStart) && screeningStart.isBefore(actualEnd)
                    ||
                    screeningStart.isBefore(actualStart) && screeningEnd.isAfter(actualEnd)
                    ||
                    screeningStart.equals(actualStart) && screeningEnd.equals(actualEnd)
            ) {
                isOverLapping = true;
            }
        }
        return isOverLapping;
    }

    public List<Screening> getScreenings() {
        return screeningRepository.findAll();
    }

    public String listScreenings() {
        if (getScreenings().isEmpty()) {
            return ("There are no screenings");
        } else {
            String s = "";
            for (var screening : getScreenings()) {
                Movie movie = movieRepository.findById(screening.getFilmName()).get();
                Room room = roomRepository.findById(screening.getRoomName()).get();

                String text = movie.getName() + " (" + movie.getType() + ", "
                        + movie.getLength() + " minutes), screened in room " + room.getName() + ", at "
                        + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(screening.getStart());
                s += text + "\n";
            }
            return s;
        }
    }

    public void deleteScreening(Screening screening) {
        if (screeningRepository.findByFilmNameAndRoomNameAndStart(screening.getFilmName(),
                screening.getRoomName(),
                screening.getStart()).isPresent()) {
            Long id = screeningRepository.findByFilmNameAndRoomNameAndStart(screening.getFilmName(),
                    screening.getRoomName(),
                    screening.getStart()).get().getId();

            screeningRepository.deleteById(id);
        } else {
            System.out.print("Screening not found");
        }

    }
}
