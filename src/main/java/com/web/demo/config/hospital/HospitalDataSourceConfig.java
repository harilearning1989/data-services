package com.web.demo.config.hospital;

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
        entityManagerFactoryRef = "hospitalEntityManagerFactory",
        transactionManagerRef = "hospitalTransactionManager",
        basePackages = {"com.web.demo.repos.hospital"})
public class HospitalDataSourceConfig {

    @Primary
    @Bean(name = "hospitalDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.hospital")
    public DataSource hospitalDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "hospitalEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean hospitalEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("hospitalDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.web.demo.models.hospital")
                .persistenceUnit("hospital")
                .build();
    }

    @Primary
    @Bean(name = "hospitalTransactionManager")
    public PlatformTransactionManager hospitalTransactionManager(
            @Qualifier("hospitalEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    /*@Primary
    @Bean(name = "primaryDataSourceProperties")
    @ConfigurationProperties("spring.datasource.primary")
    public DataSourceProperties primaryDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "hospitalDataSource")
    //@ConfigurationProperties("spring.datasource-primary.configuration")
    @ConfigurationProperties("spring.datasource.primary.configuration")
    public DataSource hospitalDataSource(
            @Qualifier("primaryDataSourceProperties") DataSourceProperties primaryDataSourceProperties) {
        return primaryDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "hospitalEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean hospitalEntityManagerFactory(
            EntityManagerFactoryBuilder primaryEntityManagerFactoryBuilder,
            @Qualifier("hospitalDataSource") DataSource hospitalDataSource) {

        *//*Map<String, String> primaryJpaProperties = new HashMap<>();
        primaryJpaProperties.put("spring.datasource.jpa.properties.hibernate.database-platform", "org.hibernate.dialect.MySQL5InnoDBDialect");
        primaryJpaProperties.put("hibernate.hbm2ddl.auto", "create-drop");*//*

        return primaryEntityManagerFactoryBuilder
                .dataSource(hospitalDataSource)
                .packages("com.web.demo.models.hospital")
                .persistenceUnit("hospitalDataSource")
                //.properties(primaryJpaProperties)
                .build();
    }

    @Primary
    @Bean(name = "hospitalTransactionManager")
    public PlatformTransactionManager hospitalTransactionManager(
            @Qualifier("hospitalEntityManagerFactory") EntityManagerFactory hospitalEntityManagerFactory) {
        return new JpaTransactionManager(hospitalEntityManagerFactory);
    }*/

}