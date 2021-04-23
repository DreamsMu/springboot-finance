package com.mhx.finance.dao;

import com.mhx.finance.domain.Capital;
import com.mhx.finance.domain.Expend;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ExpendMapper {
    List<Expend> getExpendAll(String family_id);

    List<Expend> getExpendId(Integer id);

    boolean setExpend(Expend expend);

    void delExpend(Integer id);

    List<Expend> getExpendForYear(String family_id);

    List<Expend> getExpendPayway(String family_id);

    List<Expend> getExpendWay(String family_id);

    void insertExpendData(Expend expend);
}
