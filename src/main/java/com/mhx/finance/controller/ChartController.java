package com.mhx.finance.controller;

import com.mhx.finance.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ChartController {

    @Autowired
    public ChartService chartService;

    @GetMapping("getMainData")
    public Map<Object, Object> getMainData(String family_id) {
        return chartService.getMainData(family_id);
    }

    @GetMapping("getIncomeWay")
    public List<Object> getIncomeWay(String family_id) {
        return chartService.getIncomeWay(family_id);
    }

    @GetMapping("getExpendPayway")
    public List<Object> getExpendPayway(String family_id) {
        return chartService.getExpendPayway(family_id);
    }

    @GetMapping("getExpendWay")
    public Map<Object, Object> getExpendWay(String family_id) {
        return chartService.getExpendWay(family_id);
    }
}
