package com.bat.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;
import java.util.logging.Logger;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = "com.bat")
@PropertySources(
        @PropertySource("classpath:jdbc.properties")
)
public class SpringConfig {
    @Autowired
    private Environment env;

    private Logger logger = Logger.getLogger(getClass().getName());

    private String strProp(String key) {
        return env.getProperty(key);
    }

    private int intProp(String key) {
        return Integer.parseInt(strProp(key));
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public DataSource myDataSource() {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();

        try {
            logger.info("jdbc.url=" + strProp("jdbc.url"));
            logger.info("jdbc.user=" + strProp("jdbc.user"));

            comboPooledDataSource.setDriverClass(strProp("jdbc.driver"));
            comboPooledDataSource.setJdbcUrl(strProp("jdbc.db_url"));
            comboPooledDataSource.setUser(strProp("jdbc.username"));
            comboPooledDataSource.setPassword(strProp("jdbc.password"));

            comboPooledDataSource.setInitialPoolSize(intProp("connection.initialPoolSize"));
            comboPooledDataSource.setMinPoolSize(intProp("connection.minPoolSize"));
            comboPooledDataSource.setMaxPoolSize(intProp("connection.maxPoolSize"));
            comboPooledDataSource.setMaxIdleTime(intProp("connection.maxIdleTime"));

        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        return comboPooledDataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(myDataSource());
        sessionFactory.setPackagesToScan(strProp("hibernate.packagesToScan"));

        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", strProp("hibernate.dialect"));
        properties.setProperty("hibernate.show_sql", strProp("hibernate.show_sql"));

        sessionFactory.setHibernateProperties(properties);

        return sessionFactory;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager myTransactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }
}
