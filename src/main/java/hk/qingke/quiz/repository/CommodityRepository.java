package hk.qingke.quiz.repository;

import hk.qingke.quiz.dto.CommodityDto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CommodityRepository extends CrudRepository<CommodityDto, Integer> {
    @Override
    List<CommodityDto> findAll();

    Optional<CommodityDto> findByName(String name);
}
