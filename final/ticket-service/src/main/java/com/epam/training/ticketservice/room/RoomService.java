package com.epam.training.ticketservice.room;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public void createRoom(Room room) {
        if (!roomRepository.findById(room.getName()).isPresent()) {
            roomRepository.save(room);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void updateRoom(String name, Integer numberOfRows, Integer numberOfColumns) {
        if (roomRepository.findById(name).isPresent()) {
            roomRepository.save(new Room(name, numberOfRows, numberOfColumns));
        } else {
            System.out.println("Room doest not exists");
        }
    }

    public void deleteRoom(String name) {
        if (roomRepository.findById(name).isPresent()) {
            roomRepository.deleteById(name);
        } else {
            System.out.println("Room does not exists");
        }
    }

    public void listRooms() {
        for (var room : getRooms()) {
            System.out.println(room);
        }
    }

    public List<Room> getRooms() {
        return roomRepository.findAll();
    }
}
