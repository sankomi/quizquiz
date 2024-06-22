package sanko.quiz;

import javax.sql.DataSource;

import org.springframework.context.annotation.*; //Configuration, Bean, Primary
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.session.jdbc.config.annotation.SpringSessionDataSource;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConf {

	@Bean
	@Primary
	@ConfigurationProperties("spring.datasource")
	@Qualifier("data")
	public DataSourceProperties dataProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@Primary
	public DataSource data(@Qualifier("data") DataSourceProperties data) {
		return data.initializeDataSourceBuilder()
			.type(HikariDataSource.class)
			.build();
	}

	@Bean
	@ConfigurationProperties("session.datasource")
	@Qualifier("session")
	public DataSourceProperties sessionProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@SpringSessionDataSource
	public DataSource session(@Qualifier("session") DataSourceProperties session) {
		return session.initializeDataSourceBuilder()
			.type(HikariDataSource.class)
			.build();
	}

}
