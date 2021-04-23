package com.mhx.finance.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Data
@Component
public class Capital {
    private Integer id;
    private BigDecimal total;
    private BigDecimal income;
    private BigDecimal expend;
    private BigDecimal debt;
    private String family_id;
}
