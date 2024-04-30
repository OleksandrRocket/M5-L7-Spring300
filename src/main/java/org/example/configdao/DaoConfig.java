package org.example.configdao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
@ComponentScan("org.example.jdbcdao")
@PropertySource("daocontecxt.properties")
public class DaoConfig {

    @Bean ("dataSource")
    @Profile("prod")
    public DataSource realMySqlDataSource (@Value("db.url") String url,
                                  @Value("db.name") String name,
                                  @Value ("db.password") String password){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(name);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean ("dataSource")
    @Profile("local-test")
    public DataSource inMemoryDataSource (){
            return new EmbeddedDatabaseBuilder()
                    .setType(EmbeddedDatabaseType.H2)
                    .addScripts("schema.sql", "data.sql")
                    .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate (@Qualifier("dataSource") DataSource dataSource){
        JdbcTemplate  jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate;
    }
}
