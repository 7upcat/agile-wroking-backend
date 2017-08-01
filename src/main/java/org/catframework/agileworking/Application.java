package org.catframework.agileworking;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication 
public class Application {

	@Bean
	@ConfigurationProperties(prefix = "app.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}
}