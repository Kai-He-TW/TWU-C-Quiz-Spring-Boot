package hk.qingke.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderDto {
    @Id
    @GeneratedValue
    private int id;

    private int size;

    @ManyToOne
    private CommodityDto commodityDto;
}
