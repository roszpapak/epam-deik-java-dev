package com.epam.training.ticketservice.command;


import com.epam.training.ticketservice.movie.Movie;
import com.epam.training.ticketservice.movie.MovieService;
import com.epam.training.ticketservice.room.Room;
import com.epam.training.ticketservice.room.RoomService;
import com.epam.training.ticketservice.screening.Screening;
import com.epam.training.ticketservice.screening.ScreeningService;
import com.epam.training.ticketservice.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@ShellComponent()
@AllArgsConstructor
public class TicketServiceCommandHandler {


    private final UserService userService;
    private final MovieService movieService;

    private final RoomService roomService;

    private final ScreeningService screeningService;

    @ShellMethod(value = "Create movie", key = "create movie")
    @ShellMethodAvailability(value = "isAdminLoggedIn")
    public void createMovie(String name, String type, Integer length) {
        movieService.createMovie(new Movie(name, type, length));
    }

    @ShellMethod(value = "Update movie", key = "update movie")
    @ShellMethodAvailability(value = "isAdminLoggedIn")
    public void updateMovie(String name, String type, Integer length) {
        movieService.updateMovie(name, type, length);
    }

    @ShellMethod(value = "Delete movie", key = "delete movie")
    @ShellMethodAvailability(value = "isAdminLoggedIn")
    public void deleteMovie(String name) {
        movieService.deleteMovie(name);
    }

    @ShellMethod(value = "List movies", key = "list movies")
    public void listMovies() {
        movieService.listMovies();
    }

    @ShellMethod(value = "Sign in", key = "sign in privileged")
    public void logIn(String name, String password) {
        userService.login(name, password);
    }

    @ShellMethod(value = "Sign out", key = "sign out")
    public void signOut() {
        userService.signOut();
    }

    @ShellMethod(value = "Describe account", key = "describe account")
    public String describeAccount() {
        return userService.describeAccount();
    }

    @ShellMethod(value = "Create room", key = "create room")
    @ShellMethodAvailability(value = "isAdminLoggedIn")
    public void createRoom(String name, Integer numberOfRows, Integer numberOfColumns) {
        roomService.createRoom(new Room(name,
                numberOfRows,
                numberOfColumns));
    }

    @ShellMethod(value = "Update room", key = "update room")
    @ShellMethodAvailability(value = "isAdminLoggedIn")
    public void updateRoom(String name, Integer numberOfRows, Integer numberOfColumns) {
        roomService.updateRoom(name,
                numberOfRows,
                numberOfColumns);
    }

    @ShellMethod(value = "Delete room", key = "delete room")
    @ShellMethodAvailability(value = "isAdminLoggedIn")
    public void deleteRoom(String name) {
        roomService.deleteRoom(name);
    }

    @ShellMethod(value = "List rooms", key = "list rooms")
    public void listRooms() {
        roomService.listRooms();
    }

    @ShellMethod(value = "Create screening", key = "create screening")
    @ShellMethodAvailability(value = "isAdminLoggedIn")
    public void createScreening(String filmName, String roomName, String start) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        screeningService.createScreening(new Screening(filmName, roomName, LocalDateTime.parse(start, formatter)));
    }

    @ShellMethod(value = "List screenings", key = "list screenings")
    public void listScreenings() {
        screeningService.listScreenings();
    }


    private Availability isAdminLoggedIn() {
        if (userService.isAdminLoggedIn()) {
            return Availability.available();
        } else {
            return Availability.unavailable("You are not logged in as admin");
        }
    }

}
