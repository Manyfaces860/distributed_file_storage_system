package com.dfss.distributed_file_storage_system.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.dfss.distributed_file_storage_system.repository.db1",
        entityManagerFactoryRef = "db1EntityManagerFactoryBean",
        transactionManagerRef = "db1transactionManager"
)
public class db1JPAConfig {

    @ConfigurationProperties(prefix="spring.datasource.db1")
    @Bean
    public DataSourceProperties db1DatasourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource db1Datasource() {
        return db1DatasourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    public EntityManagerFactoryBuilder db1EntityManagerFactoryBuilder() {
        return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), new HashMap<>(), null);
    }

    @Bean
    LocalContainerEntityManagerFactoryBean db1EntityManagerFactoryBean(EntityManagerFactoryBuilder entityManagerFactoryBuilder, @Qualifier("db1Datasource") DataSource dataSource) {
        Map<String, String> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.show_sql", "true");

        return entityManagerFactoryBuilder.dataSource(dataSource)
                .packages("com.dfss.distributed_file_storage_system.entity.db1")
                .properties(properties)
                .build();
    }

    @Bean
    PlatformTransactionManager db1transactionManager(@Qualifier("db1EntityManagerFactoryBean") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory.getObject());
    }

}
