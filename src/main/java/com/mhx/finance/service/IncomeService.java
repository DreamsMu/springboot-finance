package com.mhx.finance.service;

import com.github.pagehelper.PageInfo;
import com.mhx.finance.domain.Income;

import java.util.List;
import java.util.Map;


public interface IncomeService {
    PageInfo<Income> getIncomePage(Integer page, Integer rows, String family_id);

    void setIncome(Income income);

    void delIncome(Income income);

    Map<String, Object>  getYearMouth(String family_id);

    List<Income> getIncomeExpend(String family_id);

    void insertIncomeData(Income income);
}
