package com.mhx.finance.dao;

import com.mhx.finance.domain.Capital;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CapitalMapper {
    List<Capital> getCapital(String family_id);

    boolean setCapital(Capital capital);

    boolean setCapitalExpend(Capital capital);

    boolean setCapitalIncome(Capital capital);

    void setCapitalDebt(Capital capital);

    void insertCaptial(String family_id);
}
