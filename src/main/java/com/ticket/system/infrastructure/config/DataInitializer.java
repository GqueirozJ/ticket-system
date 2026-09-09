package com.ticket.system.infrastructure.config;

import com.ticket.system.domain.model.Event;
import com.ticket.system.domain.model.Ticket;
import com.ticket.system.domain.model.User;
import com.ticket.system.domain.repository.EventRepository;
import com.ticket.system.domain.repository.TicketRepository;
import com.ticket.system.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final TicketRepository ticketRepository;

    @Override
    public void run(String... args) {
        if (ticketRepository.count() == 0) {
            User user = userRepository.save(User.builder()
                    .name("Gabriel Queiroz")
                    .email("gabriel@email.com")
                    .build());

            Event event = eventRepository.save(Event.builder()
                    .name("Show de Rock 2026")
                    .totalTickets(100)
                    .availableTickets(100)
                    .price(new BigDecimal("150.00"))
                    .build());

            Ticket ticket1 = Ticket.builder().event(event).seatNumber("A1").status(Ticket.TicketStatus.AVAILABLE).build();
            Ticket ticket2 = Ticket.builder().event(event).seatNumber("A2").status(Ticket.TicketStatus.AVAILABLE).build();
            Ticket ticket3 = Ticket.builder().event(event).seatNumber("A3").status(Ticket.TicketStatus.AVAILABLE).build();

            ticketRepository.saveAll(List.of(ticket1, ticket2, ticket3));

            System.out.println(">>> Ingressos criados com sucesso! IDs disponíveis: 1, 2, 3");
        }
    }
}