package com.mhx.finance.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mhx.finance.dao.CapitalMapper;
import com.mhx.finance.dao.IncomeMapper;
import com.mhx.finance.domain.Capital;
import com.mhx.finance.domain.Expend;
import com.mhx.finance.domain.Income;
import com.mhx.finance.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IncomeServiceImpl implements IncomeService {

    @Autowired
    public IncomeMapper incomeMapper;

    @Autowired
    public CapitalMapper capitalMapper;

    @Override
    public PageInfo<Income> getIncomePage(Integer page, Integer rows,String family_id) {
        PageHelper.startPage(page, rows);
        List<Income> incomeAll = incomeMapper.getIncomeAll(family_id);
        PageInfo<Income> pageInfo = new PageInfo<>(incomeAll);
        return pageInfo;
    }

    @Override
    @Transactional
    public void setIncome(Income income) {
        //ID获取旧的钱
        Income incomeId = incomeMapper.getIncomeId(income.getId()).get(0);
        //用新钱-旧钱=变化
        BigDecimal bigDecimal = new BigDecimal(income.getPrice().subtract(incomeId.getPrice()).toString());
        //获取总资金表
        Capital capital = capitalMapper.getCapital(income.getFamily_id()).get(0);
        //总资金+变化
        capital.setTotal(capital.getTotal().add(bigDecimal));
        //总收入+变化
        capital.setIncome(capital.getIncome().add(bigDecimal));
        incomeMapper.setIncome(income);
        capitalMapper.setCapitalIncome(capital);
        if (income.getWay().equals("外借")) {
            BigDecimal bigDecimal1 = new BigDecimal("0");
            bigDecimal1 = income.getPrice().subtract(incomeId.getPrice());
            capital.setDebt(capital.getDebt().add(bigDecimal1));
            capitalMapper.setCapitalDebt(capital);
        }
    }

    @Override
    @Transactional
    public void delIncome(Income income) {
        //1.根据family_id查询家庭总资产和总收入
        Capital capital = capitalMapper.getCapital(income.getFamily_id()).get(0);
        //2.总资产-删除订单的price
        capital.setTotal(capital.getTotal().subtract(income.getPrice()));
        //3.总收入-删除订单的price
        capital.setIncome(capital.getIncome().subtract(income.getPrice()));
        incomeMapper.delIncome(income.getId());
        capitalMapper.setCapitalIncome(capital);
        if (income.getWay().equals("外借")) {
            capital.setDebt(capital.getDebt().subtract(income.getPrice()));
            capitalMapper.setCapitalDebt(capital);
        }
    }

    @Override
    @Transactional
    public Map<String, Object> getYearMouth(String family_id) {
        Map<String, Object> map= new HashMap<>();
        BigDecimal yearIncome = new BigDecimal("0.00");
        BigDecimal mouthIncome = new BigDecimal("0.00");
        Date date = new Date();
        int year = date.getYear() + 1900;
        int mouth = date.getMonth() + 1;
        List<Income> incomeAll = incomeMapper.getIncomeAll(family_id);
        for(Income income: incomeAll) {
            //计算本年的总支出
            if(year == (income.getDate().getYear()+1900)) {
                yearIncome = yearIncome.add(income.getPrice());
                //计算本月的总支出
                if (mouth == (income.getDate().getMonth()+1)){
                    mouthIncome = mouthIncome.add(income.getPrice());
                }
            }
        }
        map.put("yearIncome",yearIncome);
        map.put("mouthIncome",mouthIncome);
        return map;
    }

    @Override
    public List<Income> getIncomeExpend(String family_id) {
        return incomeMapper.getIncomeExpend(family_id);
    }

    @Override
    @Transactional
    public void insertIncomeData(Income income) {
        Capital capital = capitalMapper.getCapital(income.getFamily_id()).get(0);
        capital.setTotal(capital.getTotal().add(income.getPrice()));
        capital.setIncome(capital.getIncome().add(income.getPrice()));
        incomeMapper.insertIncomeData(income);
        capitalMapper.setCapitalIncome(capital);
        if (income.getWay().equals("外借")) {
            capital.setDebt(capital.getDebt().add(income.getPrice()));
            capitalMapper.setCapitalDebt(capital);
        }
    }


}
