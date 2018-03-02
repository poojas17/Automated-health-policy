package com.angularjsspring.crud.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories(basePackages ="com.angularjsspring.crud.dao",
		entityManagerFactoryRef = "entityManagerFactory",
		transactionManagerRef = "transactionManager")
@EnableTransactionManagement
public class JpaConfig {
	
	@Autowired
	private Environment environment;
	
	@Value("${datasource.crud.max-poolsize:10}")
	private int maxPoolSize;
	
	/**
	 * populate dataSource properties from application.properties file
	 */
	@Bean
	@Primary
	@ConfigurationProperties(prefix = "datasource.crud")
	public DataSourceProperties dataSourceProperties() {
		return new DataSourceProperties();
	}
	
	/**
	 * configure hikariCP pooled data source
	 * 
	 * @return DataSource
	 */
	@Bean
	public DataSource dataSource() {
		DataSourceProperties dataSourceProperties = dataSourceProperties();
		HikariDataSource dataSource = (HikariDataSource) DataSourceBuilder
				.create(dataSourceProperties.getClassLoader())
				.driverClassName(dataSourceProperties.getDriverClassName())
				.url(dataSourceProperties.getUrl())
				.username(dataSourceProperties.getUsername())
				.password(dataSourceProperties.getPassword())
				.type(HikariDataSource.class)
				.build();
		dataSource.setMaximumPoolSize(maxPoolSize);
		return dataSource;
	}
	
	/**
	 * Entity Manager Factory setup
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(dataSource());
		factoryBean.setPackagesToScan(new String[] {"com.angularjsspring.crud.domain"});
		factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
		factoryBean.setJpaProperties(jpaProperties());
		return factoryBean;
	}
	
	/**
	 * Jpa vendor adapter
	 */
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		return new HibernateJpaVendorAdapter();
	}
	
	private Properties jpaProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", environment.getRequiredProperty("datasource.crud.hibernate.dialect"));
		properties.put("hibernate.hbm2ddl.auto",
				environment.getRequiredProperty("datasource.crud.hibernate.hbm2ddl.method"));
		properties.put("hibernate.show_sql",
				environment.getRequiredProperty("datasource.crud.hibernate.show_sql"));
		properties.put("hibernate.format_sql",
				environment.getRequiredProperty("datasource.crud.hibernate.format_sql"));
		if (StringUtils.isNotEmpty(environment.getRequiredProperty("datasource.crud.defaultSchema"))) {
			properties.put("hibernate.default_schema",
					environment.getRequiredProperty("datasource.crud.defaultSchema"));
		}
		return properties;
	}
	
	@Bean
	@Autowired
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager txManager= new JpaTransactionManager();
		txManager.setEntityManagerFactory(emf);
		return txManager;
	}
}
