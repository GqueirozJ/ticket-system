package com.ticket.system.application.dto;

import jakarta.validation.constraints.NotNull;

public record PurchaseRequestDTO(
        @NotNull(message = "O ID do usuário é obrigatório")
        Long userId,

        @NotNull(message = "O ID do ingresso é obrigatório")
        Long ticketId
) {}