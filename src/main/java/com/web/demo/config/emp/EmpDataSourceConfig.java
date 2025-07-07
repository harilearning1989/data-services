package com.web.demo.config.emp;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "empEntityManagerFactory",
        transactionManagerRef = "empTransactionManager",
        basePackages = {"com.web.demo.repos.emp"})
public class EmpDataSourceConfig {

    @Bean(name = "empDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.emp")
    public DataSource empDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "empEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean empEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("empDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.web.demo.models.emp")
                .persistenceUnit("emp")
                .build();
    }

    @Bean(name = "empTransactionManager")
    public PlatformTransactionManager empTransactionManager(
            @Qualifier("empEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
