package hk.qingke.quiz.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private int id;

    @NotNull
    private String name;

    @NotNull
    private double price;

    @NotNull
    private String unit;

    @NotNull
    private int size;
}
