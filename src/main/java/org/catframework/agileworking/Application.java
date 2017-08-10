package org.catframework.agileworking;

import javax.sql.DataSource;

import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	// TODO 目前应用的代码和数据源的实现强关联，但是好像不这样所有的参数并不能生效
	@Bean
	public DataSource dataSource() {
		return new org.apache.tomcat.jdbc.pool.DataSource(poolProperties());
	}
	
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public PoolProperties poolProperties() {
		return new PoolProperties();
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}
}