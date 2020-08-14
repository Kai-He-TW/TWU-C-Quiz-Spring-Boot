package hk.qingke.quiz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hk.qingke.quiz.domain.Order;
import hk.qingke.quiz.dto.CommodityDto;
import hk.qingke.quiz.dto.OrderDto;
import hk.qingke.quiz.repository.CommodityRepository;
import hk.qingke.quiz.repository.OrderRepository;
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
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CommodityRepository commodityRepository;

    private ObjectMapper objectMapper;
    private CommodityDto commodityDto;
    private Map<String, OrderDto> orderDtos;

    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper();

        this.commodityDto = CommodityDto.builder()
                .name("TEST COMMODITY")
                .price(2.5)
                .unit("bottle")
                .imageUrl("TEST:IMAGE:URL")
                .build();
        this.commodityDto = this.commodityRepository.save(commodityDto);

        String commodityName = this.commodityDto.getName();

        this.orderDtos = new HashMap<>();
        for (int i = 0; i < 15; i++) {
            OrderDto orderDto = OrderDto.builder()
                    .size(i + 5)
                    .commodityDto(this.commodityDto)
                    .build();

            orderDto = this.orderRepository.save(orderDto);
            this.orderDtos.put(commodityName + ":" + orderDto.getSize(), orderDto);
        }
    }

    @Test
    void should_get_order_list_success() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/order"))
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        assertThat(content).isNotNull();

        Order[] orders = this.objectMapper.readValue(content, Order[].class);
        for (Order order : orders) {
            String orderDtoKey = order.getName() + ":" + order.getSize();
            OrderDto orderDto = this.orderDtos.get(orderDtoKey);

            assertThat(order.getSize()).isEqualTo(orderDto.getSize());
            assertThat(order.getName()).isEqualTo(this.commodityDto.getName());
            assertThat(order.getPrice()).isEqualTo(this.commodityDto.getPrice());
            assertThat(order.getUnit()).isEqualTo(this.commodityDto.getUnit());
        }
    }

    @Test
    void should_add_commodity_into_order_success() throws Exception {
        CommodityDto commodityDto = CommodityDto.builder()
                .name("TEST COMMODITY 2")
                .price(2.5)
                .unit("bottle")
                .imageUrl("TEST:IMAGE:URL")
                .build();
        commodityDto = this.commodityRepository.save(commodityDto);

        Order order = Order.builder()
                .name(commodityDto.getName())
                .price(commodityDto.getPrice())
                .unit(commodityDto.getUnit())
                .size(5)
                .build();

        String orderJson = this.objectMapper.writeValueAsString(order);

        MockHttpServletRequestBuilder requestBuilder = post("/order").content(orderJson).contentType(MediaType.APPLICATION_JSON);
        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated());

        List<OrderDto> orderDtos = this.orderRepository.findAll();
        assertThat(orderDtos.size()).isEqualTo(16);
    }

    @Test
    void should_add_commodity_into_order_fail_when_commodity_is_not_existed() throws Exception {
        Order order = Order.builder()
                .name("NOT EXISTED")
                .price(55.5)
                .unit("NOT EXISTED")
                .size(5)
                .build();

        String orderJson = this.objectMapper.writeValueAsString(order);

        MockHttpServletRequestBuilder requestBuilder = post("/order").content(orderJson).contentType(MediaType.APPLICATION_JSON);
        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }
}