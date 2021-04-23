package com.mhx.finance.service;

import com.github.pagehelper.PageInfo;
import com.mhx.finance.domain.Expend;

import java.util.Map;

public interface ExpendService {
    PageInfo<Expend> getExpendPage(Integer page, Integer rows, String family_id);

    void setExpend(Expend expend);

    void delExpend(Expend expend);

    Map<String, Object> getYearMouth(String family_id);

    void insertExpendData(Expend expend);
}
