import com.epam.training.ticketservice.movie.Movie;
import com.epam.training.ticketservice.movie.MovieRepository;
import com.epam.training.ticketservice.movie.MovieService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {


    MovieService underTest;
    @Mock
    private MovieRepository movieRepository;

    @BeforeEach
    public void setUp() {
        underTest = new MovieService(
                movieRepository
        );
    }

    @Test
    void testListProductsShouldReturnEmptyListWhenNoProductsAdded() {
        // Given

        // When
        List<Movie> actualResult = underTest.getMovies();
        // Then
        Assertions.assertEquals(Collections.emptyList(), actualResult);
    }
}
