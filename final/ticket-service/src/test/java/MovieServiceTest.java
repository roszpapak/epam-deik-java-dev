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
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {


    MovieService underTest;

    private Movie harryMovie = new Movie("Harry Potter", "fantasy", 160);
    @Mock
    private MovieRepository movieRepository;

    @BeforeEach
    public void setUp() {
        underTest = new MovieService(
                movieRepository
        );
    }

    @Test
    void testListMoviesShouldReturnEmptyListWhenNoMoviesAdded() {
        // Given

        // When
        List<Movie> actualResult = underTest.getMovies();
        // Then
        Assertions.assertEquals(Collections.emptyList(), actualResult);
    }


    @Test
    void testListMoviesShouldReturnTheListOfMoviesWhenNotEmpty() {
        // Given
        List<Movie> movies = Collections.singletonList(harryMovie);
        given(underTest.getMovies()).willReturn(movies);
        // When


        final List<Movie> actual = underTest.getMovies();
        // Then
        Assertions.assertEquals(movies, actual);
    }

    @Test
    void testAddNewMovie() {
        //Given
        //When
        underTest.createMovie(harryMovie);
        //Then
        verify(movieRepository).save(harryMovie);

    }

    @Test
    void testUpdateMovie() {
        //Given
        Movie movie = new Movie("Harry Potter", "fantasy", 150);
        given(movieRepository.findById(harryMovie.getName())).willReturn(Optional.of(movie));
        //When
        underTest.updateMovie("Harry Potter", "fantasy", 160);
        //Then
        verify(movieRepository).save(harryMovie);


    }


    @Test
    void testListMovies() {

        //Given
        given(movieRepository.findAll()).willReturn(List.of());
        //When
        underTest.listMovies();
        //Then
        verify(movieRepository).findAll();
    }

    @Test
    void testListMoviesWithoutMovies() {

        //Given
        //When
        underTest.listMovies();
        //Then
        verify(movieRepository).findAll();
    }

    @Test
    void testDeleteMovies() {
        //Given
        given(movieRepository.findById(harryMovie.getName())).willReturn(Optional.of(harryMovie));
        //When
        underTest.deleteMovie(harryMovie.getName());
        //Then
        verify(movieRepository).deleteById(harryMovie.getName());
    }
}
