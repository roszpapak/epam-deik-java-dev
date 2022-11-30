import com.epam.training.ticketservice.movie.MovieRepository;
import com.epam.training.ticketservice.room.RoomRepository;
import com.epam.training.ticketservice.screening.Screening;
import com.epam.training.ticketservice.screening.ScreeningRepository;
import com.epam.training.ticketservice.screening.ScreeningService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ScreeningServiceTest {
    private ScreeningService underTest;

    @Mock
    private ScreeningRepository screeningRepository;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private MovieRepository movieRepository;


    private Screening example = new Screening("Harry Potter", "Elso", LocalDateTime.now());


    @BeforeEach
    public void setUp() {
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
        BDDMockito.given(underTest.getScreenings()).willReturn(screenings);
        // When
        final List<Screening> actual = underTest.getScreenings();
        // Then
        Assertions.assertEquals(screenings, actual);
    }

    @Test
    void testAddNewScreening() {
        //Given
        BDDMockito.given(movieRepository.existsById(example.getFilmName())).willReturn(true);
        BDDMockito.given(roomRepository.existsById(example.getRoomName())).willReturn(true);
        //When
        underTest.createScreening(example);
        //Then
        verify(screeningRepository).save(example);

    }


}
