package hk.qingke.quiz.repository;

import hk.qingke.quiz.dto.CommodityDto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommodityRepository extends CrudRepository<CommodityDto, Integer> {
    @Override
    List<CommodityDto> findAll();
}
