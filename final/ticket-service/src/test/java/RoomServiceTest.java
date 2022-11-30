import com.epam.training.ticketservice.room.Room;
import com.epam.training.ticketservice.room.RoomRepository;
import com.epam.training.ticketservice.room.RoomService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private RoomService underTest;
    @Mock
    private RoomRepository roomRepository;

    private Room example = new Room("First", 25, 25);


    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        underTest = new RoomService(
                roomRepository
        );
    }

    @AfterEach
    public void restoreSout() {
        System.setOut(originalOut);
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
        given(roomRepository.findAll()).willReturn(List.of(example));
        String expected = "Room First with 625 seats, 25 rows and 25 columns" + "\n";
        //When
        String actual = underTest.listRooms();
        //Then
        Assertions.assertEquals(expected, actual);

    }

    @Test
    void testListRoomsWithoutRooms() {

        //Given
        given(roomRepository.findAll()).willReturn(List.of());
        String expected = "There are no rooms at the moment";
        //When
        String actual = underTest.listRooms();
        //Then
        Assertions.assertEquals(expected, actual);
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


    @Test
    void updateRoom() {
        //Given
        given(roomRepository.findById(example.getName())).willReturn(Optional.empty());
        //When
        underTest.updateRoom(example.getName(), example.getNumberOfRows(), example.getNumberOfRows());
        //Then
        Assertions.assertEquals("Room does not exists", outContent.toString());

    }

    @Test
    void deleteRoom() {
        //Given
        given(roomRepository.findById(example.getName())).willReturn(Optional.empty());
        //When
        underTest.deleteRoom(example.getName());
        //Then
        Assertions.assertEquals("Room does not exists", outContent.toString());

    }

}
