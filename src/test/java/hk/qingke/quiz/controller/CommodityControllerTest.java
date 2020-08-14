package hk.qingke.quiz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hk.qingke.quiz.domain.Commodity;
import hk.qingke.quiz.dto.CommodityDto;
import hk.qingke.quiz.repository.CommodityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CommodityControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CommodityRepository commodityRepository;

    private ObjectMapper objectMapper;
    private Map<String, CommodityDto> commodityDtos;

    @BeforeEach
    void setup() {
        this.objectMapper = new ObjectMapper();

        this.commodityDtos = new HashMap<>();
        for (int i = 0; i < 15; i++) {
            CommodityDto commodityDto = CommodityDto.builder()
                    .name("TEST: " + i)
                    .price(2.5 + i)
                    .unit("bottle")
                    .imageUrl("TEST:IMAGE:URL")
                    .build();
            commodityDto = this.commodityRepository.save(commodityDto);
            this.commodityDtos.put(commodityDto.getName(), commodityDto);
        }
    }

    @Test
    void should_get_commodities_success() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/commodity"))
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        assertThat(content).isNotNull();

        Commodity[] commodities = this.objectMapper.readValue(content, Commodity[].class);

        assertThat(commodities.length).isEqualTo(this.commodityDtos.size());

        for (Commodity commodity : commodities) {
            CommodityDto commodityDto = this.commodityDtos.get(commodity.getName());
            assertThat(commodity.getName()).isEqualTo(commodityDto.getName());
            assertThat(commodity.getPrice()).isEqualTo(commodityDto.getPrice());
            assertThat(commodity.getUnit()).isEqualTo(commodityDto.getUnit());
            assertThat(commodity.getImageUrl()).isEqualTo(commodityDto.getImageUrl());
        }
    }
}
