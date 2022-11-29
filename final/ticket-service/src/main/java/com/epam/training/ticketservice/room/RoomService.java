package com.epam.training.ticketservice.room;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public void createRoom(String name,Integer numberOfRows,Integer numberOfColumns){
        if(!roomRepository.findById(name).isPresent()){
            roomRepository.save(new Room(name,numberOfRows,numberOfColumns));
        }else {
            System.out.println("Room already exists");
        }
    }

    public void updateRoom(String name,Integer numberOfRows,Integer numberOfColumns){
        if(roomRepository.findById(name).isPresent()){
            roomRepository.save(new Room(name,numberOfRows,numberOfColumns));
        }else {
            System.out.println("Room doest not exists");
        }
    }

    public void deleteRoom(String name){
        if(roomRepository.findById(name).isEmpty()){
            roomRepository.delete(roomRepository.findById(name).get());
        }else {
            System.out.println("Room does not exists");
        }
    }

    public void listRooms(){
        if(roomRepository.findAll().isEmpty()){
            System.out.println("There are no rooms at the moment");
        }else {
            for(var room : roomRepository.findAll()){
                System.out.println(room);
            }
        }
    }
}
