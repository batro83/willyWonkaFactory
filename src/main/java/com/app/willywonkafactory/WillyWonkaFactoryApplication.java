package com.app.willywonkafactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class,
DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class WillyWonkaFactoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(WillyWonkaFactoryApplication.class, args);
	}

}
