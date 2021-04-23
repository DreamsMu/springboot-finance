package com.mhx.finance.controller;

import com.github.pagehelper.PageInfo;
import com.mhx.finance.domain.Income;
import com.mhx.finance.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class IncomeController {

    @Autowired
    public IncomeService incomeService;

    @GetMapping("getIncomePage")
    public PageInfo<Income> getIncomePage(Integer page, Integer rows, String family_id) {
        return incomeService.getIncomePage(page, rows, family_id);
    }

    @PostMapping("setIncome")
    public Integer setIncome(Income income) {
        try {
            incomeService.setIncome(income);
            return 200;
        } catch (Exception e) {
            e.printStackTrace();
            return 201;
        }
    }

    @PostMapping("delIncome")
    public Integer delIncome(Income income) {
        try {
            incomeService.delIncome(income);
            return 200;
        } catch (Exception e) {
            e.printStackTrace();
            return 201;
        }
    }

    @GetMapping("getYearMouthIncome")
    public Map<String, Object> getYearMouthIncome(String family_id) {
        return incomeService.getYearMouth(family_id);
    }

    @GetMapping("getIncomeExpend")
    public List<Income> getIncomeExpend(String family_id) {
        return incomeService.getIncomeExpend(family_id);
    }

    @PostMapping("insertIncomeData")
    public Integer insertIncomeData(Income income) {
        try {
            incomeService.insertIncomeData(income);
            return 200;
        } catch (Exception e) {
            e.printStackTrace();
            return 201;
        }
    }
}
