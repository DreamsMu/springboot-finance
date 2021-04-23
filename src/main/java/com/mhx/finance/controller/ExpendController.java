package com.mhx.finance.controller;

import com.github.pagehelper.PageInfo;
import com.mhx.finance.domain.Expend;
import com.mhx.finance.service.ExpendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ExpendController {

    @Autowired
    public ExpendService expendService;

    @GetMapping("getExpendPage")
    public PageInfo<Expend> getExpendPage(Integer page, Integer rows, String family_id) {
        PageInfo<Expend> expendPage = expendService.getExpendPage(page, rows, family_id);
        return expendPage;
    }

    @PostMapping("setExpend")
    public Integer setExpend(Expend expend) {
        try {
            expendService.setExpend(expend);
            return 200;
        } catch (Exception e) {
            e.printStackTrace();
            return 201;
        }
    }

    @PostMapping("delExpend")
    public Integer delExpend(Expend expend) {
        try {
            expendService.delExpend(expend);
            return 200;
        } catch (Exception e) {
            e.printStackTrace();
            return 201;
        }
    }

    @GetMapping("getYearMouthExpend")
    public Map<String, Object> getYearMouthExpend(String family_id) {
        return expendService.getYearMouth(family_id);
    }

    @PostMapping("insertExpendData")
    public Integer insertExpendData(Expend expend) {
        try{
            expendService.insertExpendData(expend);
            return 200;
        } catch (Exception e) {
            e.printStackTrace();
            return 201;
        }
    }
}
