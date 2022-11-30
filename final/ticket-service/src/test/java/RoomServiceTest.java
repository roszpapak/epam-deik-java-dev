import com.epam.training.ticketservice.room.Room;
import com.epam.training.ticketservice.room.RoomRepository;
import com.epam.training.ticketservice.room.RoomService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    private RoomService underTest;

    @Mock
    private RoomRepository roomRepository;

    private Room example = new Room("First", 25, 25);


    @BeforeEach
    public void setUp() {
        underTest = new RoomService(
                roomRepository
        );
    }

    @Test
    void testListRoomsShouldReturnEmptyListWhenNoRoomsAdded() {
        // Given

        // When
        List<Room> actualResult = underTest.getRooms();
        // Then
        Assertions.assertEquals(Collections.emptyList(), actualResult);
    }


    @Test
    void testListRoomsShouldReturnTheListOfRoomsWhenNotEmpty() {
        // Given
        List<Room> rooms = Collections.singletonList(example);
        BDDMockito.given(underTest.getRooms()).willReturn(rooms);
        // When
        final List<Room> actual = underTest.getRooms();
        // Then
        Assertions.assertEquals(rooms, actual);
    }

    @Test
    void testAddNewRoom() {
        //Given
        //When
        underTest.createRoom(example);
        //Then
        verify(roomRepository).save(example);

    }


    @Test
    void testListRooms() {

        //Given
        given(roomRepository.findAll()).willReturn(List.of());
        //When
        underTest.listRooms();
        //Then
        verify(roomRepository).findAll();
    }

    @Test
    void testListRoomsWithoutRooms() {

        //Given
        //When
        underTest.listRooms();
        //Then
        verify(roomRepository).findAll();
    }

    @Test
    void testUpdateMovie() {
        //Given
        Room room = new Room("First", 20, 25);
        given(roomRepository.findById(example.getName())).willReturn(Optional.of(example));
        //When
        underTest.updateRoom("First", 25, 25);
        //Then
        verify(roomRepository).save(example);
    }

    @Test
    void testDeleteRoom() {
        //Given
        given(roomRepository.findById(example.getName())).willReturn(Optional.of(example));
        //When
        underTest.deleteRoom(example.getName());
        //Then
        verify(roomRepository).deleteById(example.getName());
    }

}
