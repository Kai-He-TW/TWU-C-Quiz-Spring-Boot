package hk.qingke.quiz.controller;

import hk.qingke.quiz.domain.Order;
import hk.qingke.quiz.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order")
    public ResponseEntity<List<Order>> list() {
        List<Order> orders = this.orderService.list();
        return ResponseEntity.ok(orders);
    }
}
