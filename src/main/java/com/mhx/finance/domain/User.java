package com.mhx.finance.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Data
@Component
public class User {
    private Integer id;
    private String name;
    private String username;
    private String password;
    private String power;
    private Integer master;
    private String family_id;
    private BigDecimal total;
    private BigDecimal  debt;
}
