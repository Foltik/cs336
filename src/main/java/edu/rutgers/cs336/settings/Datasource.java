package edu.rutgers.cs336.settings;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class Datasource {
    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
            .url(env.getProperty("DB_URL", "jdbc:mysql://localhost/cs336"))
            .username("cs336")
            .password("cs336")
            .build();
    }
}
