package com.ticket.system.application.dto;

import com.ticket.system.domain.model.Order;

import java.time.LocalDateTime;

public record OrderResponseDTO(
        Long orderId,
        Long userId,
        Long ticketId,
        String seatNumber,
        String status,
        LocalDateTime createdAt
) {
    public static OrderResponseDTO fromEntity(Order order) {
        return new OrderResponseDTO(
                order.getId(),
                order.getUser().getId(),
                order.getTicket().getId(),
                order.getTicket().getSeatNumber(),
                order.getStatus().name(),
                order.getCreatedAt()
        );
    }
}