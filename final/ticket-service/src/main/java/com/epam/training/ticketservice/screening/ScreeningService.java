package com.epam.training.ticketservice.screening;

import com.epam.training.ticketservice.movie.Movie;
import com.epam.training.ticketservice.movie.MovieRepository;
import com.epam.training.ticketservice.room.Room;
import com.epam.training.ticketservice.room.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;


    public void createScreening(String movieName, String roomName, String start){
        if(movieRepository.existsById(movieName) && roomRepository.existsById(roomName)){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            screeningRepository.save(new Screening(movieName,roomName,LocalDateTime.parse(start,formatter)));
        }else {
            System.out.println("No movie or room found");
        }

    }

    public void listScreenings(){
        if(screeningRepository.findAll().isEmpty()){
            System.out.println("There are no screenings");
        }else {
            for(var screening: screeningRepository.findAll()){
                Movie movie = movieRepository.findById(screening.getFilmName()).get();
                Room room = roomRepository.findById(screening.getRoomName()).get();

                String text = movie.getName() + " (" + movie.getType() +", " + movie.getLength() +" minutes), screened in room " + room.getName() +", at " +
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(screening.getStart());
                System.out.println(text);
            }
        }
    }
}
