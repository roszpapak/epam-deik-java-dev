import com.epam.training.ticketservice.movie.Movie;
import com.epam.training.ticketservice.movie.MovieRepository;
import com.epam.training.ticketservice.room.Room;
import com.epam.training.ticketservice.room.RoomRepository;
import com.epam.training.ticketservice.screening.Screening;
import com.epam.training.ticketservice.screening.ScreeningRepository;
import com.epam.training.ticketservice.screening.ScreeningService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ScreeningServiceTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private ScreeningService underTest;
    @Mock
    private ScreeningRepository screeningRepository;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private MovieRepository movieRepository;
    private Screening example = new Screening("Harry Potter", "First", LocalDateTime.of(2022, 11, 30, 22, 47));
    private Movie movieExample = new Movie("Harry Potter", "fantasy", 160);

    private Room roomExample = new Room("First", 25, 25);

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        underTest = new ScreeningService(
                screeningRepository,
                movieRepository,
                roomRepository
        );
    }

    @Test
    void testListScreeningsShouldReturnEmptyListWhenNoScreeningsAdded() {
        // Given

        // When
        List<Screening> actualResult = underTest.getScreenings();
        // Then
        Assertions.assertEquals(Collections.emptyList(), actualResult);
    }


    @Test
    void testListScreeningsShouldReturnTheListOfScreeningsWhenNotEmpty() {
        // Given
        List<Screening> screenings = Collections.singletonList(example);
        given(underTest.getScreenings()).willReturn(screenings);
        // When
        final List<Screening> actual = underTest.getScreenings();
        // Then
        Assertions.assertEquals(screenings, actual);
    }

    @Test
    void testAddNewScreening() {
        //Given
        given(movieRepository.existsById(example.getFilmName())).willReturn(true);
        given(roomRepository.existsById(example.getRoomName())).willReturn(true);
        given(movieRepository.findById(example.getFilmName())).willReturn(Optional.of(movieExample));
        given(screeningRepository.findByRoomName(example.getRoomName())).willReturn(List.of());
        String expected = "User succesfully added";
        //When
        String actual = underTest.createScreening(example);
        //Then
        Assertions.assertEquals(actual, expected);

    }


    @Test
    void testDeleteScreeningWhenScreenIsFound() {

        //Given
        given(screeningRepository.findByFilmNameAndRoomNameAndStart
                (example.getFilmName(), example.getRoomName(), example.getStart())).willReturn(Optional.of(example));
        //When
        underTest.deleteScreening(example);
        //Then
        verify(screeningRepository).deleteById(example.getId());
    }

    @Test
    void testDeleteScreeningWhenScreenNotFound() {

        //Given
        given(screeningRepository.findByFilmNameAndRoomNameAndStart
                (example.getFilmName(), example.getRoomName(), example.getStart())).willReturn(Optional.empty());
        //When
        underTest.deleteScreening(example);
        //Then
        Assertions.assertEquals("Screening not found", outContent.toString());

    }


    @Test
    void testListScreeningsWhenListIsEmpty() {
        //Given
        given(screeningRepository.findAll()).willReturn(List.of());
        String expected = "There are no screenings";
        //When
        String actual = underTest.listScreenings();
        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testListScreeningsWhenListIsNotEmpty() {
        //Given
        given(screeningRepository.findAll()).willReturn(List.of(example));
        given(movieRepository.findById(example.getFilmName())).willReturn(Optional.of(movieExample));
        given(roomRepository.findById(example.getRoomName())).willReturn(Optional.of(roomExample));
        String expected = "Harry Potter (fantasy, 160 minutes), screened in room First, at 2022-11-30 22:47" + "\n";
        //When
        String actual = underTest.listScreenings();
        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testOverLapping() {
        //Given
        given(movieRepository.findById(example.getFilmName())).willReturn(Optional.of(movieExample));
        List<Screening> screeningList = List.of(example);
        //When
        boolean actual = underTest.isOverLapping(screeningList, example);
        //Then
        Assertions.assertTrue(actual);
    }


    @Test
    void testOverLappingWhenItIsFalse() {
        //Given
        given(movieRepository.findById(example.getFilmName())).willReturn(Optional.of(movieExample));
        List<Screening> screeningList = List.of();
        //When
        boolean actual = underTest.isOverLapping(screeningList, example);
        //Then
        Assertions.assertFalse(actual);
    }


    @Test
    void testIsInBreakWhenItIs() {
        //Given
        Screening screening = new Screening("Harry Potter", "First", LocalDateTime.of(2022, 11, 30, 22, 47).minusMinutes(165));
        given(movieRepository.findById(example.getFilmName())).willReturn(Optional.of(movieExample));
        List<Screening> screeningList = List.of(screening);
        //When
        boolean actual = underTest.isInBreak(screeningList, example);
        //Then
        Assertions.assertTrue(actual);
    }

    @Test
    void testIsInBreakWhenItIsNot() {
        //Given
        given(movieRepository.findById(example.getFilmName())).willReturn(Optional.of(movieExample));
        List<Screening> screeningList = List.of(example);
        //When
        boolean actual = underTest.isInBreak(screeningList, example);
        //Then
        Assertions.assertFalse(actual);
    }

}


