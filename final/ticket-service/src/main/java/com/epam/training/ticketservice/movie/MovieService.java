package com.epam.training.ticketservice.movie;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public void createMovie(String name,String type,Integer length){
        if(movieRepository.findById(name).isEmpty()){
            movieRepository.save(new Movie(name,type,length));
        }else System.out.println("Film with name already exists.");

    }

    public List<Movie> getMovies(){
        return movieRepository.findAll();
    }

    public void listMovies(){
        if(movieRepository.findAll().isEmpty()){
            System.out.println("There are no movies at the moment");
        }else {
            for(var film : movieRepository.findAll()){
                System.out.println(film);
            }
        }

    }

    public void updateMovie(String name,String type,Integer length){
        if(movieRepository.findById(name).isPresent()){
            Movie movie = movieRepository.findById(name).get();
            movie.setType(type);
            movie.setLength(length);
            movieRepository.save(movie);
        }else System.out.println("Film not found");
    }

    public void deleteMovie(String name){
        if(movieRepository.findById(name).isPresent()){
            movieRepository.deleteById(name);
        }else System.out.println("Film not found");
    }

}