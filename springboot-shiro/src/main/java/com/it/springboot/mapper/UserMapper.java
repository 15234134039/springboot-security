package com.it.springboot.mapper;

import com.it.springboot.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Mapper 这个注解表示了这是一个mybatis的mapper类
 */
@Mapper
@Repository
public interface UserMapper {

    List<User> queryUserList();

    User queryUserById(int id);

    User queryUserByName(String username);

    int addUser(User user);

    int updateUser(User user);

    int deleteUser(int id);
}
