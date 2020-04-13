package com.it.springboot.config;

import com.it.springboot.pojo.User;
import com.it.springboot.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义Realm
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了=>授权doGetAuthorizationInfo");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //拿到当前登录的这个对象
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User) subject.getPrincipal();

        //得到权限
        info.addStringPermission(currentUser.getPerms());

        return info;
    }

    /**
     * 认证   连接数据库
     * @param
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了=>认证doGetAuthenticationInfo");

        UsernamePasswordToken userToken = (UsernamePasswordToken) token;

        User user = userService.queryUserByName(userToken.getUsername());
        if(user == null){
            //抛出异常 UnknownAccountException
            return null;
        }

        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        session.setAttribute("loginUser", user);


        //密码认证，shiro做，加密了
        return new SimpleAuthenticationInfo(user, user.getPassword(), "");
    }

    /**
     * 认证   不连接数据库
     * @param
     * @return
     * @throws AuthenticationException
     */
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//        System.out.println("执行了=>认证doGetAuthenticationInfo");
//
//        String name = "root";
//        String password = "123456";
//
//        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
//
//        if(!userToken.getUsername().equals(name)){
//            //抛出异常 UnknownAccountException
//            return null;
//        }
//
//        //密码认证，shiro做
//        return new SimpleAuthenticationInfo("", password, "");
//    }
}
