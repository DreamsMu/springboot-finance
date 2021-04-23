package com.mhx.finance.service.impl;

import com.mhx.finance.dao.ExpendMapper;
import com.mhx.finance.dao.IncomeMapper;
import com.mhx.finance.domain.Expend;
import com.mhx.finance.domain.Income;
import com.mhx.finance.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ChartServiceImpl implements ChartService {

    @Autowired
    public IncomeMapper incomeMapper;

    @Autowired
    public ExpendMapper expendMapper;

    @Override
    public Map<Object, Object> getMainData(String family_id) {
        List<Income> income = incomeMapper.getIncomeForYear(family_id);
        List<Expend> expend = expendMapper.getExpendForYear(family_id);
        Map<Object, Object> map = new HashMap<>();
        List<Object> mouthList = new ArrayList<>();
        List<BigDecimal> incomeList = new ArrayList<>();
        List<BigDecimal> expendList = new ArrayList<>();
        List<BigDecimal> totalList = new ArrayList<>();
        Date date = new Date();
        int year = date.getYear() + 1900;
        int mouth = date.getMonth() + 1;

        if (mouth != 12) {
            for (int i = (mouth+1); i <= 12; i++ ) {
                if (i == 1) mouthList.add((year - 1)+"-"+ i+"月");
                else mouthList.add(i + "月");

                BigDecimal bigDecimal = new BigDecimal("0");
                for (Income item : income) {
                    if ((item.getDate().getYear() + 1900) == (year - 1) && (item.getDate().getMonth() + 1) == i) {
                        bigDecimal = bigDecimal.add(item.getPrice());
                    }
                }
                incomeList.add(bigDecimal);

                BigDecimal bigDecimal1 = new BigDecimal("0");
                for (Expend item : expend) {
                    if ((item.getDate().getYear() + 1900) == (year - 1) && (item.getDate().getMonth() + 1) == i) {
                        bigDecimal1 = bigDecimal1.add(item.getPrice());
                    }
                }
                expendList.add(bigDecimal1);

            }
        }
        for (int i = 1; i <= mouth; i++) {
            if (i == 1) mouthList.add(year +"/"+ i+"月");
            else mouthList.add(i + "月");

            BigDecimal bigDecimal = new BigDecimal("0");
            for (Income item : income) {
                if ((item.getDate().getYear() + 1900) == year && (item.getDate().getMonth() + 1) == i) {
                    bigDecimal = bigDecimal.add(item.getPrice());
                }
            }
            incomeList.add(bigDecimal);

            BigDecimal bigDecimal1 = new BigDecimal("0");
            for (Expend item : expend) {
                if ((item.getDate().getYear() + 1900) == year && (item.getDate().getMonth() + 1) == i) {

                    bigDecimal1 = bigDecimal1.add(item.getPrice());
                }
            }
            expendList.add(bigDecimal1);
        }

        BigDecimal bigDecimal2 = new BigDecimal("0");
        for (int i = 0; i < incomeList.toArray().length; i++){
            bigDecimal2 = bigDecimal2.add(incomeList.get(i).subtract(expendList.get(i)));
            totalList.add(bigDecimal2);
        }

        map.put("mouth", mouthList);
        map.put("income", incomeList);
        map.put("expend", expendList);
        map.put("total", totalList);
        return map;
    }

    @Override
    public List<Object> getIncomeWay(String family_id) {
        List<Object> list = new ArrayList<>();
        List<Income> income = incomeMapper.getIncomeWay(family_id);
        for(Income item: income) {
            Map<Object, Object> map = new HashMap<>();
            map.put("value",item.getMold());
            map.put("name",item.getWay());
            list.add(map);
        }
        return list;
    }

    @Override
    public List<Object> getExpendPayway(String family_id) {
        List<Object> list = new ArrayList<>();
        List<Expend> expend = expendMapper.getExpendPayway(family_id);
        for(Expend item: expend) {
            Map<Object, Object> map = new HashMap<>();
            map.put("value",item.getMold());
            map.put("name",item.getPayway());
            list.add(map);
        }
        return list;
    }

    @Override
    public Map<Object, Object> getExpendWay(String family_id) {
        Map<Object, Object> map = new HashMap<>();
        List<Object> name = new ArrayList<>();
        List<Object> value = new ArrayList<>();
        List<Expend> expend = expendMapper.getExpendWay(family_id);
        for(Expend item: expend) {
            name.add(item.getWay());
            value.add(item.getPrice());
        }
        map.put("name",name);
        map.put("value",value);
        return map;
    }
}
