package com.it.springboot;

import com.it.springboot.pojo.User;

import com.it.springboot.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = SpringBootShiroApliaction.class)
@RunWith(SpringRunner.class)
public class SpringBootShiroApplicationTest {

    @Autowired
    UserService userService;

    @Test
    public void contextLoad(){
        List<User> users = userService.queryUserList();
        System.out.println(users);
    }
}
