package com.web.demo.config.order;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "orderEntityManagerFactory",
        transactionManagerRef = "orderTransactionManager",
        basePackages = {"com.web.demo.repos.order"})
public class OrderDataSourceConfig {

    @Bean(name = "orderDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.order")
    public DataSource orderDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "orderEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean orderEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("orderDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.web.demo.models.order")
                .persistenceUnit("order")
                .build();
    }

    @Primary
    @Bean(name = "orderTransactionManager")
    public PlatformTransactionManager orderTransactionManager(
            @Qualifier("orderEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    /*@Bean(name = "orderDataSourceProperties")
    @ConfigurationProperties("spring.datasource.order")
    public DataSourceProperties orderDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "orderDataSource")
    @ConfigurationProperties("spring.datasource.order.configuration")
    public DataSource orderDataSource(
            @Qualifier("orderDataSourceProperties") DataSourceProperties orderDataSourceProperties) {
        return orderDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean(name = "orderEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean orderEntityManagerFactory(
            EntityManagerFactoryBuilder orderEntityManagerFactoryBuilder,
            @Qualifier("orderDataSource") DataSource orderDataSource) {

        *//*Map<String, String> orderJpaProperties = new HashMap<>();
        orderJpaProperties.put("spring.datasource.jpa.properties.hibernate.database-platform", "org.hibernate.dialect.MySQL5InnoDBDialect");
        orderJpaProperties.put("hibernate.hbm2ddl.auto", "create-drop");*//*

        return orderEntityManagerFactoryBuilder
                .dataSource(orderDataSource)
                .packages("com.web.demo.models.order")
                .persistenceUnit("orderDataSource")
                //.properties(orderJpaProperties)
                .build();
    }

    @Bean(name = "orderTransactionManager")
    @ConfigurationProperties("spring.jpa")
    public PlatformTransactionManager orderTransactionManager(
            @Qualifier("orderEntityManagerFactory") EntityManagerFactory orderEntityManagerFactory) {
        return new JpaTransactionManager(orderEntityManagerFactory);
    }*/
}
