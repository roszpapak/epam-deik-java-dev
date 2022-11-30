import com.epam.training.ticketservice.command.TicketServiceCommandHandler;
import com.epam.training.ticketservice.movie.Movie;
import com.epam.training.ticketservice.movie.MovieService;
import com.epam.training.ticketservice.room.Room;
import com.epam.training.ticketservice.room.RoomService;
import com.epam.training.ticketservice.screening.Screening;
import com.epam.training.ticketservice.screening.ScreeningService;
import com.epam.training.ticketservice.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TicketServiceCommandHandlerTest {

    private TicketServiceCommandHandler underTest;

    @Mock
    private UserService userService;

    @Mock
    private MovieService movieService;

    @Mock
    private RoomService roomService;

    @Mock
    private ScreeningService screeningService;

    @BeforeEach
    public void setUp() {
        underTest = new TicketServiceCommandHandler(
                userService,
                movieService,
                roomService,
                screeningService
        );
    }

    @Test
    void testCreateMovie() {
        //Given

        //When
        underTest.createMovie("Harry", "fantasy", 160);
        //Then
        verify(movieService).createMovie(new Movie("Harry", "fantasy", 160));
    }

    @Test
    void testUpdateMovie() {
        //Given

        //When
        underTest.updateMovie("Harry", "fantasy", 160);
        //Then
        verify(movieService).updateMovie("Harry", "fantasy", 160);
    }

    @Test
    void testDeleteMovie() {
        //Given

        //When
        underTest.deleteMovie("Harry");
        //Then
        verify(movieService).deleteMovie("Harry");
    }

    @Test
    void testListMovies() {
        //Given

        //When
        underTest.listMovies();
        //Then
        verify(movieService).listMovies();
    }

    @Test
    void testLogIn() {
        //Given

        //When
        underTest.logIn("admin", "admin");
        //Then
        verify(userService).login("admin", "admin");
    }

    @Test
    void testSignOut() {
        //Given

        //When
        underTest.signOut();
        //Then
        verify(userService).signOut();
    }

    @Test
    void testDescribeAccount() {
        //Given

        //When
        underTest.describeAccount();
        //Then
        verify(userService).describeAccount();
    }

    @Test
    void testCreateRoom() {
        //Given

        //When
        underTest.createRoom("First", 25, 25);
        //Then
        verify(roomService).createRoom(new Room("First", 25, 25));
    }

    @Test
    void testUpdateRoom() {
        //Given

        //When
        underTest.updateRoom("First", 25, 25);
        //Then
        verify(roomService).updateRoom("First", 25, 25);
    }

    @Test
    void testDeleteRoom() {
        //Given

        //When
        underTest.deleteRoom("First");
        //Then
        verify(roomService).deleteRoom("First");
    }

    @Test
    void testListRooms() {
        //Given

        //When
        underTest.listRooms();
        //Then
        verify(roomService).listRooms();
    }

    @Test
    void testCreateScreening() {
        //Given

        //When
        underTest.createScreening("Harry Potter", "First", "2021-03-14 16:00");
        //Then
        verify(screeningService).createScreening(new Screening("Harry Potter", "First", LocalDateTime.of(2021, 03, 14, 16, 00)));
    }

    @Test
    void testListScreenings() {
        //Given

        //When
        underTest.listScreenings();
        //Then
        verify(screeningService).listScreenings();
    }


}