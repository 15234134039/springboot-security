package com.it.springboot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String password;
    private String username;
    private String sex;
    private String address;
    private Date birthday;
    private String perms;
}
