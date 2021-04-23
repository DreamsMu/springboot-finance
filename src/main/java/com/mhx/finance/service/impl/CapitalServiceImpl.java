package com.mhx.finance.service.impl;

import com.mhx.finance.dao.CapitalMapper;
import com.mhx.finance.domain.Capital;
import com.mhx.finance.service.CapitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CapitalServiceImpl implements CapitalService {

    @Autowired
    public CapitalMapper capitalMapper;

    @Override
    public List<Capital> getCapital(String family_id) {
        return capitalMapper.getCapital(family_id);
    }

    @Override
    @Transactional
    public void setCapital(Capital capital) {
        capitalMapper.setCapital(capital);
    }
}
