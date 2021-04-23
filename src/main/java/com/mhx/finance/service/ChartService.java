package com.mhx.finance.service;

import java.util.List;
import java.util.Map;

public interface ChartService {
    Map<Object,Object> getMainData(String family_id);

    List<Object> getIncomeWay(String family_id);

    List<Object> getExpendPayway(String family_id);

    Map<Object, Object> getExpendWay(String family_id);
}
