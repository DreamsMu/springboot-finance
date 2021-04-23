package com.mhx.finance.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mhx.finance.dao.CapitalMapper;
import com.mhx.finance.dao.ExpendMapper;
import com.mhx.finance.domain.Capital;
import com.mhx.finance.domain.Expend;
import com.mhx.finance.service.ExpendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExpendServiceImpl implements ExpendService {

    @Autowired
    public ExpendMapper expendMapper;

    @Autowired
    public CapitalMapper capitalMapper;

    @Override
    public PageInfo<Expend> getExpendPage(Integer page,Integer rows, String family_id) {
        PageHelper.startPage(page, rows);
        List<Expend> expendAll = expendMapper.getExpendAll(family_id);
        PageInfo<Expend> pageInfo = new PageInfo<>(expendAll);
        return pageInfo;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void setExpend(Expend expend) {
        //ID获取旧的钱
        Expend expendId = expendMapper.getExpendId(expend.getId()).get(0);
        //用新钱-旧钱=变化
        BigDecimal bigDecimal = new BigDecimal(expend.getPrice().subtract(expendId.getPrice()).toString());
        //获取总资金表
        Capital capital = capitalMapper.getCapital(expend.getFamily_id()).get(0);
        //总资金-变化
        capital.setTotal(capital.getTotal().subtract(bigDecimal));
        //总支出+变化
        capital.setExpend(capital.getExpend().add(bigDecimal));
        expendMapper.setExpend(expend);
        capitalMapper.setCapitalExpend(capital);
        if (expend.getWay().equals("债务") || expend.equals("分期还款")) {
            BigDecimal bigDecimal1 = new BigDecimal("0");
            bigDecimal1 = expend.getPrice().subtract(expendId.getPrice());
            capital.setDebt(capital.getDebt().subtract(bigDecimal1));
            capitalMapper.setCapitalDebt(capital);
        }
    }

    @Override
    @Transactional
    public void delExpend(Expend expend) {
        //1.根据family_id查询家庭总资产和总支出
        Capital capital = capitalMapper.getCapital(expend.getFamily_id()).get(0);
        //2.总资产+删除订单的price
        capital.setTotal(capital.getTotal().add(expend.getPrice()));
        //3.总支出-删除订单的price
        capital.setExpend(capital.getExpend().subtract(expend.getPrice()));
        expendMapper.delExpend(expend.getId());
        capitalMapper.setCapitalExpend(capital);
        if (expend.getWay().equals("债务") || expend.equals("分期还款")) {
            capital.setDebt(capital.getDebt().add(expend.getPrice()));
            capitalMapper.setCapitalDebt(capital);
        }
    }

    @Override
    @Transactional
    public Map<String, Object> getYearMouth(String family_id) {
        Map<String, Object> map= new HashMap<>();
        BigDecimal yearExpend = new BigDecimal("0.00");
        BigDecimal mouthExpend = new BigDecimal("0.00");
        Date date = new Date();
        int year = date.getYear() + 1900;
        int mouth = date.getMonth() + 1;
        List<Expend> expendAll = expendMapper.getExpendAll(family_id);
        for(Expend expend: expendAll) {
            //计算本年的总支出
            if(year == (expend.getDate().getYear()+1900)) {
                yearExpend = yearExpend.add(expend.getPrice());
                //计算本月的总支出
                if (mouth == (expend.getDate().getMonth()+1)){
                    mouthExpend = mouthExpend.add(expend.getPrice());
                }
            }
        }
        map.put("yearExpend",yearExpend);
        map.put("mouthExpend",mouthExpend);
        return map;
    }

    @Override
    @Transactional
    public void insertExpendData(Expend expend) {
        Capital capital = capitalMapper.getCapital(expend.getFamily_id()).get(0);
        if (expend.getPayway().equals("分期付款")) {
            capital.setDebt(capital.getDebt().add(expend.getPrice()));
            capitalMapper.setCapitalDebt(capital);
        } else {
            capital.setTotal(capital.getTotal().subtract(expend.getPrice()));
            capital.setExpend(capital.getExpend().add(expend.getPrice()));
            expendMapper.insertExpendData(expend);
        }
        capitalMapper.setCapitalExpend(capital);
        if (expend.getWay().equals("债务") || expend.equals("分期还款")) {
            capital.setDebt(capital.getDebt().subtract(expend.getPrice()));
            capitalMapper.setCapitalDebt(capital);
        }

    }
}
