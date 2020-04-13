package com.it.springboot.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfig {

    /**
     * 将自定义的 Druid数据源添加到容器中，不再让 Spring Boot 自动创建
     * 绑定全局配置文件中的 druid 数据源属性到 com.alibaba.druid.pool.DruidDataSource从而让它们生效
     * @ConfigurationProperties(prefix = "spring.datasource")：作用就是将 全局配置文件中
     * 前缀为 spring.datasource的属性值注入到 com.alibaba.druid.pool.DruidDataSource 的同名参数中
     * @return
     */
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }

    /**
     * 后台监控
     * 配置 Druid 监控管理后台的Servlet；
     * springboot 内置 Servlet 容器时没有web.xml文件，所以使用 Spring Boot 的注册 Servlet 方式
     */
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<StatViewServlet>(new StatViewServlet(),  "/druid/*");

        /**
         * 后台登录，账号密码配置
         * 这些参数可以在 com.alibaba.druid.support.http.StatViewServlet
         * 的父类 com.alibaba.druid.support.http.ResourceServlet 中找到
         */
        HashMap<String, String> initParameters = new HashMap<String, String>();
        initParameters.put("loginUsername", "admin");
        initParameters.put("loginPassword", "123456");

        /**
         * 后台允许谁可以访问
         * initParams.put("allow", "localhost")：表示只有本机可以访问
         * initParams.put("allow", "")：为空或者为null时，表示允许所有访问
         */
        initParameters.put("allow", "");

        //设置初始化参数
        bean.setInitParameters(initParameters);
        return bean;
    }

    /**
     * filter
     * 配置 Druid 监控 之  web 监控的 filter
     * WebStatFilter：用于配置Web和Druid数据源之间的管理关联监控统计
     */
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());

        //exclusions：设置哪些请求进行过滤排除掉，从而不进行统计
        Map<String, String> initParameters = new HashMap<String, String>();
        initParameters.put("exclusions", "*.js,*.css,/druid/*");
        bean.setInitParameters(initParameters);

        return bean;
    }
}
