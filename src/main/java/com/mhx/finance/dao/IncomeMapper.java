package com.mhx.finance.dao;

import com.mhx.finance.domain.Income;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IncomeMapper {
    List<Income> getIncomeAll(String family_id);

    List<Income> getIncomeId(Integer id);

    boolean setIncome(Income income);

    void delIncome(Integer id);

    List<Income> getIncomeExpend(String family_id);

    List<Income> getIncomeForYear(String family_id);

    List<Income> getIncomeWay(String family_id);

    void insertIncomeData(Income income);

}
