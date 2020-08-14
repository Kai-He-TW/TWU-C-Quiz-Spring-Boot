package hk.qingke.quiz.service;

import hk.qingke.quiz.domain.Commodity;
import hk.qingke.quiz.dto.CommodityDto;
import hk.qingke.quiz.repository.CommodityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommodityService {
    private final CommodityRepository commodityRepository;

    public CommodityService(CommodityRepository commodityRepository) {
        this.commodityRepository = commodityRepository;
    }

    public void save(Commodity commodity) {
        Optional<CommodityDto> commodityDto = this.commodityRepository.findByName(commodity.getName());

        if (commodityDto.isPresent()) {
            throw new RuntimeException("commodity existed");
        }

        this.commodityRepository.save(this.domainToDto(commodity));
    }

    private CommodityDto domainToDto(Commodity commodity) {
        return CommodityDto.builder()
                .name(commodity.getName())
                .price(commodity.getPrice())
                .unit(commodity.getUnit())
                .imageUrl(commodity.getImageUrl())
                .build();
    }

    public List<Commodity> list() {
        return this.commodityRepository.findAll()
                .stream()
                .map(this::dtoToDomain)
                .collect(Collectors.toList());
    }

    private Commodity dtoToDomain(CommodityDto commodity) {
        return Commodity.builder()
                .name(commodity.getName())
                .price(commodity.getPrice())
                .unit(commodity.getUnit())
                .imageUrl(commodity.getImageUrl())
                .build();
    }
}
