package com.epam.training.ticketservice.command;

import org.jline.utils.AttributedString;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class TicketServiceCommandPrompt implements PromptProvider {
    @Override
    public AttributedString getPrompt() {
        return new AttributedString("Ticket service>");
    }
}
