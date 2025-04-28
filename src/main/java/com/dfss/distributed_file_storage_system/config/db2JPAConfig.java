//package com.dfss.distributed_file_storage_system.config;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//
//
//@Configuration
//@EnableJpaRepositories(
//        basePackages = "com.dfss.distributed_file_storage_system.repository.db2",
//        entityManagerFactoryRef = "db2EntityManagerFactoryBean",
//        transactionManagerRef = "db2transactionManager"
//)
//public class db2JPAConfig {
//
//    @ConfigurationProperties(prefix="spring.datasource.db2")
//    @Bean
//    public DataSourceProperties db2DatasourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    @Bean
//    public DataSource db2Datasource() {
//        return db2DatasourceProperties().initializeDataSourceBuilder().build();
//    }
//
//
//    @Bean
//    LocalContainerEntityManagerFactoryBean db2EntityManagerFactoryBean(EntityManagerFactoryBuilder entityManagerFactoryBuilder, @Qualifier("db2Datasource") DataSource dataSource) {
//        Map<String, String> properties = new HashMap<>();
//        properties.put("hibernate.hbm2ddl.auto", "update");
//        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
//        properties.put("hibernate.show_sql", "true");
//
//        return entityManagerFactoryBuilder.dataSource(dataSource)
//                .packages("com.dfss.distributed_file_storage_system.entity.db2")
//                .properties(properties)
//                .build();
//    }
//
//    @Bean
//    PlatformTransactionManager db2transactionManager(@Qualifier("db2EntityManagerFactoryBean") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory.getObject());
//    }
//
//}
