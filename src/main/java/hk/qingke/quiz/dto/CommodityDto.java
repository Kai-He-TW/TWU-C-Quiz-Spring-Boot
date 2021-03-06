package hk.qingke.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CommodityDto {
    @Id
    @GeneratedValue
    private int id;

    private String name;
    private double price;
    private String unit;
    private String imageUrl;

    @OneToMany(mappedBy = "commodityDto")
    private List<OrderDto> orderDtos;
}
