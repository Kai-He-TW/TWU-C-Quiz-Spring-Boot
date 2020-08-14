package hk.qingke.quiz.controller;

import hk.qingke.quiz.domain.Order;
import hk.qingke.quiz.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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

    @PostMapping("/order")
    public ResponseEntity<Object> addCommodity(@RequestBody @Valid Order order) {
        try {
            this.orderService.save(order);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
