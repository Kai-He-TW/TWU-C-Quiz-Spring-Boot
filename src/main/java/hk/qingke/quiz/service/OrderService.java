package hk.qingke.quiz.service;

import hk.qingke.quiz.domain.Order;
import hk.qingke.quiz.dto.CommodityDto;
import hk.qingke.quiz.dto.OrderDto;
import hk.qingke.quiz.repository.CommodityRepository;
import hk.qingke.quiz.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CommodityRepository commodityRepository;

    public OrderService(OrderRepository orderRepository, CommodityRepository commodityRepository) {
        this.orderRepository = orderRepository;
        this.commodityRepository = commodityRepository;
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

    public void save(Order order) {
        Optional<CommodityDto> commodityDto = this.commodityRepository.findByName(order.getName());
        if (!commodityDto.isPresent()) {
            throw new RuntimeException("commodity is not existed");
        }

        Optional<OrderDto> orderDtoOptional = this.orderRepository.findByCommodityDto(commodityDto.get());
        if (orderDtoOptional.isPresent()) {
            OrderDto orderDto = orderDtoOptional.get();
            orderDto.setSize(orderDto.getSize() + order.getSize());
            this.orderRepository.save(orderDto);
            return;
        }

        OrderDto orderDto = OrderDto.builder()
                .size(order.getSize())
                .commodityDto(commodityDto.get())
                .build();

        this.orderRepository.save(orderDto);
    }

    public void delete(int id) {
        Optional<OrderDto> orderDto = this.orderRepository.findById(id);
        if (!orderDto.isPresent()) {
            throw new RuntimeException("order not existed");
        }

        this.orderRepository.delete(orderDto.get());
    }
}
