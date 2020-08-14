package hk.qingke.quiz.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Commodity {
    private String name;
    private double price;
    private String unit;
    private String imageUrl;
}
