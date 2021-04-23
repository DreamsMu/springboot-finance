package com.mhx.finance.service;

import com.mhx.finance.domain.Capital;
import com.mhx.finance.domain.Expend;

import java.util.List;
import java.util.Map;

public interface CapitalService {

    List<Capital> getCapital(String family_id);

    void setCapital(Capital capital);
}
