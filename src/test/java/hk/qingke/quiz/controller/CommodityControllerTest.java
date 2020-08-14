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
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Test
    void should_add_commodity_success() throws Exception {
        Commodity commodity = Commodity.builder()
                .name("ADD TEST SUCCESS")
                .price(30.4)
                .unit("bottle")
                .imageUrl("TEST:ADD:IMAGE")
                .build();

        String commodityJson = this.objectMapper.writeValueAsString(commodity);

        MockHttpServletRequestBuilder requestBuilder = post("/commodity").content(commodityJson).contentType(MediaType.APPLICATION_JSON);
        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated());
    }

    @Test
    void should_add_commodity_fail_when_commodity_name_is_existed() throws Exception {
        String commodityJson = this.objectMapper.writeValueAsString(this.commodityDtos.get("TEST: 1"));

        MockHttpServletRequestBuilder requestBuilder = post("/commodity").content(commodityJson).contentType(MediaType.APPLICATION_JSON);
        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_add_commodity_fail_when_name_is_null() throws Exception {
        Commodity commodity = Commodity.builder()
                .name(null)
                .price(30.4)
                .unit("bottle")
                .imageUrl("TEST:ADD:IMAGE")
                .build();

        String commodityJson = this.objectMapper.writeValueAsString(commodity);

        MockHttpServletRequestBuilder requestBuilder = post("/commodity").content(commodityJson).contentType(MediaType.APPLICATION_JSON);
        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_add_commodity_fail_when_price_is_not_number() throws Exception {
        String commodityJson = "{\n" +
                "  \"name\": \"ADD TEST\",\n" +
                "  \"price\": \"not number\",\n" +
                "  \"unit\": \"bottle\",\n" +
                "  \"imageUrl\": \"TEST:ADD:IMAGE\"\n" +
                "}";

        MockHttpServletRequestBuilder requestBuilder = post("/commodity").content(commodityJson).contentType(MediaType.APPLICATION_JSON);
        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_add_commodity_fail_when_unit_is_null() throws Exception {
        Commodity commodity = Commodity.builder()
                .name("ADD TEST SUCCESS")
                .price(30.4)
                .unit(null)
                .imageUrl("TEST:ADD:IMAGE")
                .build();

        String commodityJson = this.objectMapper.writeValueAsString(commodity);

        MockHttpServletRequestBuilder requestBuilder = post("/commodity").content(commodityJson).contentType(MediaType.APPLICATION_JSON);
        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_add_commodity_fail_when_image_url_is_null() throws Exception {
        Commodity commodity = Commodity.builder()
                .name("ADD TEST SUCCESS")
                .price(30.4)
                .unit("bottle")
                .imageUrl(null)
                .build();

        String commodityJson = this.objectMapper.writeValueAsString(commodity);

        MockHttpServletRequestBuilder requestBuilder = post("/commodity").content(commodityJson).contentType(MediaType.APPLICATION_JSON);
        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }
}
