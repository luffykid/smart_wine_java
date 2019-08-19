package com.changfa.frame.website.config.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Druid配置类
 * User: WYY
 * Date: 2019-04-10
 */
@Configuration
public class DruidConfig {

    private static final transient Logger logger = LoggerFactory.getLogger(DruidConfig.class);

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.initialSize}")
    private int initialSize;

    @Value("${spring.datasource.minIdle}")
    private int minIdle;

    @Value("${spring.datasource.maxActive}")
    private int maxActive;

    @Value("${spring.datasource.maxWait}")
    private int maxWait;

    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${spring.datasource.validationQuery}")
    private String validationQuery;

    @Value("${spring.datasource.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${spring.datasource.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${spring.datasource.testOnReturn}")
    private boolean testOnReturn;

    @Value("${spring.datasource.poolPreparedStatements}")
    private boolean poolPreparedStatements;

    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;

    @Value("${spring.datasource.filters}")
    private String filters;

    @Value("{spring.datasource.connectionProperties}")
    private String connectionProperties;

    /**
     * @return
     * @Bean 声明，DataSource 对象为 Spring 容器所管理;
     * @Primary 表示这里定义的DataSource将覆盖其他来源的DataSource。
     * StatFilter，用于统计监控信息。StatFilter的别名是stat。
     * 统计SQL信息，合并统计。mergeStat是的MergeStatFilter缩写。
     * 通过 DataSource 的属性<property name="filters" value="mergeStat" /> 或者
     * connectProperties属性来打开mergeSql功能 <property name="connectionProperties" value="druid.stat.mergeSql=true" />
     * StatFilter属性slowSqlMillis用来配置SQL慢的标准
     */
    @Bean
    @Primary
    public DataSource dataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(dbUrl);
        datasource.setDriverClassName(driverClassName);
        datasource.setUsername(username);
        datasource.setPassword(password);

        //configuration
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setPoolPreparedStatements(poolPreparedStatements);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        try {
            /**
             * 设置StatFilter，用于统计监控信息。
             * StatFilter的别名是stat
             *
             */
            datasource.setFilters(filters);
        } catch (SQLException e) {
            logger.error("druid configuration initialization filter : {0}", e);
        }
        datasource.setConnectionProperties(connectionProperties);

        return datasource;
    }

    /**
     * 配置监控服务器
     * @return 返回监控注册的servlet对象
     * @author SimpleWu
     */
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        // 添加IP白名单
        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
        // 添加IP黑名单，当白名单和黑名单重复时，黑名单优先级更高
        servletRegistrationBean.addInitParameter("deny", "127.0.0.1");
        // 添加控制台管理用户
        servletRegistrationBean.addInitParameter("loginUsername", "SimpleWu");
        servletRegistrationBean.addInitParameter("loginPassword", "123456");
        // 是否能够重置数据
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    /**
     * 配置服务过滤器
     *
     * @return 返回过滤器配置对象
     */
    @Bean
    public FilterRegistrationBean statFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        // 添加过滤规则
        filterRegistrationBean.addUrlPatterns("/*");
        // 忽略过滤格式
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*,");
        return filterRegistrationBean;
    }

}