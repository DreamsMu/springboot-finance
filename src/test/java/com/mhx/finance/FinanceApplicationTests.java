package com.mhx.finance;

import com.mhx.finance.dao.CapitalMapper;
import com.mhx.finance.dao.ExpendMapper;
import com.mhx.finance.dao.IncomeMapper;
import com.mhx.finance.dao.UserMapper;
import com.mhx.finance.domain.Capital;
import com.mhx.finance.domain.Expend;
import com.mhx.finance.domain.User;
import com.mhx.finance.service.ChartService;
import com.mhx.finance.service.ExpendService;
import com.mhx.finance.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@SpringBootTest
class FinanceApplicationTests {

    @Autowired
    public UserMapper userMapper;

    @Autowired
    public UserService userService;

    @Autowired
    public CapitalMapper capitalMapper;

    @Autowired
    public ExpendMapper expendMapper;

    @Autowired
    public ExpendService expendService;

    @Autowired
    public ChartService chartService;

    @Autowired
    public IncomeMapper incomeMapper;

    @Test
    void contextLoads() {
    }

}
