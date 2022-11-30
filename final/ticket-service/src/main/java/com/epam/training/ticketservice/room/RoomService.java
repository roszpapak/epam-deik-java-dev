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
        }
    }

    public void updateRoom(String name, Integer numberOfRows, Integer numberOfColumns) {
        if (roomRepository.findById(name).isPresent()) {
            roomRepository.save(new Room(name, numberOfRows, numberOfColumns));
        } else {
            System.out.print("Room does not exists");
        }
    }

    public void deleteRoom(String name) {
        if (roomRepository.findById(name).isPresent()) {
            roomRepository.deleteById(name);
        } else {
            System.out.print("Room does not exists");
        }
    }

    public String listRooms() {
        if (getRooms().isEmpty()) {
            return "There are no rooms at the moment";
        } else {
            String s = "";
            for (var room : getRooms()) {
                s += room.toString() + "\n";
            }
            return s;
        }

    }

    public List<Room> getRooms() {
        return roomRepository.findAll();
    }
}