package hk.qingke.quiz.controller;

import hk.qingke.quiz.domain.Commodity;
import hk.qingke.quiz.service.CommodityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommodityController {

    private final CommodityService commodityService;

    public CommodityController(CommodityService commodityService) {
        this.commodityService = commodityService;
    }

    @GetMapping("/commodity")
    public ResponseEntity<List<Commodity>> list() {
        List<Commodity> commodities = this.commodityService.list();
        return ResponseEntity.ok(commodities);
    }
}
