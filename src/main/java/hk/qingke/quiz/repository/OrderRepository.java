package hk.qingke.quiz.repository;

import hk.qingke.quiz.dto.CommodityDto;
import hk.qingke.quiz.dto.OrderDto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends CrudRepository<OrderDto, Integer> {
    @Override
    List<OrderDto> findAll();

    Optional<OrderDto> findByCommodityDto(CommodityDto commodityDto);
}
