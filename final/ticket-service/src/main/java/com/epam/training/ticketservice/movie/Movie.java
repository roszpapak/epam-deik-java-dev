package com.epam.training.ticketservice.movie;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    private String name;
    private String type;
    private Integer length;


    @Override
    public String toString() {
        return name + " (" +type + ", " + length + " minutes)";
    }
}
