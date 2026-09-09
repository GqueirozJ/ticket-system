package com.ticket.system.application.service;

import com.ticket.system.application.dto.PurchaseRequestDTO;
import com.ticket.system.domain.model.Order;
import com.ticket.system.domain.model.Ticket;
import com.ticket.system.domain.model.User;
import com.ticket.system.domain.repository.OrderRepository;
import com.ticket.system.domain.repository.TicketRepository;
import com.ticket.system.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final RedissonClient redissonClient;

    @Transactional
    public Order purchaseTicket(PurchaseRequestDTO request) {
        String lockKey = "lock:ticket:" + request.ticketId();
        RLock lock = redissonClient.getLock(lockKey);

        try {
            // Tenta adquirir a trava por até 5 segundos; libera após 10 segundos
            boolean isAcquired = lock.tryLock(5, 10, TimeUnit.SECONDS);

            if (!isAcquired) {
                throw new IllegalStateException("O ingresso está sendo processado por outra compra. Tente novamente.");
            }

            Ticket ticket = ticketRepository.findById(request.ticketId())
                    .orElseThrow(() -> new IllegalArgumentException("Ingresso não encontrado."));

            if (ticket.getStatus() != Ticket.TicketStatus.AVAILABLE) {
                throw new IllegalStateException("Ingresso indisponível para compra.");
            }

            User user = userRepository.findById(request.userId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

            // Atualiza status do ingresso
            ticket.setStatus(Ticket.TicketStatus.SOLD);
            ticketRepository.save(ticket);

            // Cria o pedido
            Order order = Order.builder()
                    .user(user)
                    .ticket(ticket)
                    .status(Order.OrderStatus.PAID)
                    .build();

            return orderRepository.save(order);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Erro ao processar a compra do ingresso.", e);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}