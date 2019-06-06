package com.player.toys.erp.server.logic.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ServerMenuServiceImplTest {

    @Resource
    private ServerMenuServiceImpl serverMenuService;

    @Test
    public void getServerMenuByUserId() {
        System.out.println(new BCryptPasswordEncoder().encode("123456123"));;

    }
}