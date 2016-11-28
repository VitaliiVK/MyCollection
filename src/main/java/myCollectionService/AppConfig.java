package myCollectionService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

//springIoS Configuration class
@Configuration
@PropertySource("classpath:config.properties") //path to configuration file for auto parsing Spring
@EnableTransactionManagement
@EnableWebMvc
public class AppConfig{

    @Value("${hibernate.dialect}") //in file from @PropertySource find field hibernate.dialect
    private String sqlDialect; // auto initialize in runtime

    @Value("${hbm2ddl.auto}") //in file from @PropertySource find field hbm2ddl.auto
    private String hbm2dllAuto; // auto initialize in runtime

    @Bean //Bean create EntityManagerFactory for data base
    //field DataSource dataSource will auto initialize from Properties additionalProperties()
    //DataSource dataSource is contain jdbc driver properties
    public LocalContainerEntityManagerFactoryBean entityManagerFactory
            (DataSource dataSource, JpaVendorAdapter jpaVendeorAdapter) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setJpaVendorAdapter(jpaVendeorAdapter);
        entityManagerFactory.setJpaProperties(additionalProperties());
        entityManagerFactory.setPackagesToScan("myCollectionService/dataBaseEntitys");
        return entityManagerFactory;
    }

    @Bean //Bean for @transaction annotations
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
        return new JpaTransactionManager(emf);
    }

    @Bean //Bean instead of persistence.xml for data base
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(true);
        adapter.setDatabasePlatform(sqlDialect);
        return adapter;
    }

    @Bean //Bean specify path too .jsp pages, and page format
    public UrlBasedViewResolver setupViewResolver() {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setPrefix("/WEB-INF/pages/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        return resolver;
    }

    @Bean //Bean for transfer files in Multipart format
    public CommonsMultipartResolver multipartResolver() {
        return new CommonsMultipartResolver();
    }

    @Bean //Bean adapter between my Collector class and User class from SpringSecurity
    public UserDetailsService userDetailsService(){
        return new UserDetailsServiceImpl();
    }

    //Been for DataSource dataSource create
    private Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", hbm2dllAuto);
        return properties;
    }

    /*
    @Bean //instead application.properties
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/testsec");
        ds.setUsername("root");
        ds.setPassword("testpass");
        return ds;
    }
    */
}
