package com.epam.training.ticketservice.movie;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public void createMovie(Movie movie) {
        if (movieRepository.findById(movie.getName()).isEmpty()) {
            movieRepository.save(movie);
        } else {
            throw new IllegalArgumentException("Film already exists");
        }


    }

    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    public String listMovies() {
        if (getMovies().isEmpty()) {
            return ("There are no movies at the moment");
        } else {
            String s = "";
            for (var film : getMovies()) {
                s += film.toString() + "\n";
            }
            return s;
        }
    }

    public void updateMovie(String name, String type, Integer length) {
        if (movieRepository.findById(name).isPresent()) {
            Movie movie = movieRepository.findById(name).get();
            movie.setType(type);
            movie.setLength(length);
            movieRepository.save(movie);
        } else {
            throw new IllegalArgumentException("Movie not found");
        }
    }

    public void deleteMovie(String name) {
        if (movieRepository.findById(name).isPresent()) {
            movieRepository.deleteById(name);
        } else {
            System.out.println("Film not found");
        }
    }

}
