package com.ticket.system.infrastructure.controller;

import com.ticket.system.application.dto.OrderResponseDTO;
import com.ticket.system.application.dto.PurchaseRequestDTO;
import com.ticket.system.application.service.TicketService;
import com.ticket.system.domain.model.Order;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/purchase")
    public ResponseEntity<OrderResponseDTO> purchaseTicket(@RequestBody @Valid PurchaseRequestDTO request) {
        Order order = ticketService.purchaseTicket(request);
        return ResponseEntity.ok(OrderResponseDTO.fromEntity(order));
    }
}