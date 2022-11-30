package com.epam.training.ticketservice.screening;

import com.epam.training.ticketservice.movie.Movie;
import com.epam.training.ticketservice.movie.MovieRepository;
import com.epam.training.ticketservice.room.Room;
import com.epam.training.ticketservice.room.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
public class ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;


    public void createScreening(Screening screening) {
        if (movieRepository.existsById(screening.getFilmName()) && roomRepository.existsById(screening.getRoomName())) {
            screeningRepository.save(screening);
        } else {
            throw new IllegalArgumentException();
        }

    }

    public List<Screening> getScreenings() {
        return screeningRepository.findAll();
    }

    public void listScreenings() {
        if (getScreenings().isEmpty()) {
            System.out.println("There are no screenings");
        } else {
            for (var screening : getScreenings()) {
                Movie movie = movieRepository.findById(screening.getFilmName()).get();
                Room room = roomRepository.findById(screening.getRoomName()).get();

                String text = movie.getName() + " (" + movie.getType() + ", " + movie.getLength() + " minutes), screened in room " + room.getName() + ", at " +
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(screening.getStart());
                System.out.println(text);
            }
        }
    }
}
