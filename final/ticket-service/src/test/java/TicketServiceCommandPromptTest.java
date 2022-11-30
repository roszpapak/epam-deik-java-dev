import com.epam.training.ticketservice.command.TicketServiceCommandPrompt;
import org.jline.utils.AttributedString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class TicketServiceCommandPromptTest {

    private TicketServiceCommandPrompt undertest;

    @Test
    void getPrompt() {
        undertest = new TicketServiceCommandPrompt();
        AttributedString attributedString = new AttributedString("Ticket service>");
        Assertions.assertEquals(undertest.getPrompt(), attributedString);
    }
}