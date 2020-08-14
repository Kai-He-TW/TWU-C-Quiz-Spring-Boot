package hk.qingke.quiz.service;

import hk.qingke.quiz.domain.Order;
import hk.qingke.quiz.dto.OrderDto;
import hk.qingke.quiz.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    public List<Order> list() {
        return this.orderRepository.findAll()
                .stream()
                .map(this::dtoToDomain)
                .collect(Collectors.toList());
    }

    private Order dtoToDomain(OrderDto orderDto) {
        return Order.builder()
                .name(orderDto.getCommodityDto().getName())
                .price(orderDto.getCommodityDto().getPrice())
                .unit(orderDto.getCommodityDto().getUnit())
                .size(orderDto.getSize())
                .build();
    }
}
