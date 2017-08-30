package org.catframework.agileworking;

import javax.sql.DataSource;

import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.catframework.agileworking.web.support.WebTokenHandlerInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableScheduling
public class Application extends WebMvcConfigurerAdapter {

	@Bean
	public DataSource dataSource() {
		return new org.apache.tomcat.jdbc.pool.DataSource(poolProperties());
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public PoolProperties poolProperties() {
		return new PoolProperties();
	}

	@Bean
	WebTokenHandlerInterceptor webTokenHandlerInterceptor() {
		return new WebTokenHandlerInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(webTokenHandlerInterceptor()).addPathPatterns("/**");
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}
}