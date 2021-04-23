package com.mhx.finance.controller;

import com.mhx.finance.domain.Capital;
import com.mhx.finance.service.CapitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CapitalController {

    @Autowired
    public CapitalService capitalService;

    @GetMapping("getCapital")
    public Capital getCapital(String family_id) {
        return capitalService.getCapital(family_id).get(0);
    }

}
