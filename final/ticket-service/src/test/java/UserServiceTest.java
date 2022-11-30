import com.epam.training.ticketservice.user.User;
import com.epam.training.ticketservice.user.UserRepository;
import com.epam.training.ticketservice.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {


    private UserService underTest;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        underTest = new UserService(
                userRepository
        );
    }


    @Test
    void testLoginWhenNameAndPasswordIsAdmin() {
        //Given
        //When
        underTest.login("admin", "admin");
        //Then
        verify(userRepository).save(new User("admin", "admin"));
    }

    @Test
    void testSingOut() {
        //Given
        given(userRepository.findByNameAndPassword("admin", "admin")).
                willReturn(Optional.of(new User("admin", "admin")));
        //When
        underTest.signOut();
        //Then
        verify(userRepository).delete(new User("admin", "admin"));
    }

    @Test
    void testDescribeAccountWhenLoggedIn() {
        //Given
        given(userRepository.findByNameAndPassword("admin", "admin")).
                willReturn(Optional.of(new User("admin", "admin")));
        //When
        String actual = underTest.describeAccount();
        //Then
        Assertions.assertEquals("Signed in with privileged account admin", actual);
    }

    @Test
    void testDescribeAccountWhenLoggedOut() {
        //Given
        given(userRepository.findByNameAndPassword("admin", "admin")).
                willReturn(Optional.empty());
        //When
        String actual = underTest.describeAccount();
        //Then
        Assertions.assertEquals("You are not signed in", actual);
    }

    @Test
    void testIsAdminLoggedIn() {
        //Given
        given(userRepository.findByNameAndPassword("admin", "admin"))
                .willReturn(Optional.of(new User("admin", "admin")));
        //When
        Boolean actual = underTest.isAdminLoggedIn();
        //Then
        Assertions.assertTrue(actual);
    }

    @Test
    void testIsAdminLoggedInWhenLoggedOut() {
        //Given
        given(userRepository.findByNameAndPassword("admin", "admin"))
                .willReturn(Optional.empty());
        //When
        Boolean actual = underTest.isAdminLoggedIn();
        //Then
        Assertions.assertFalse(actual);
    }


}
