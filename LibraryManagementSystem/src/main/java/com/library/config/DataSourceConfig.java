package com.library.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.net.URI;

@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.url:jdbc:postgresql://localhost:5433/LibraryManagementSystem}")
    private String datasourceUrl;

    @Value("${spring.datasource.username:postgres}")
    private String username;

    @Value("${spring.datasource.password:123}")
    private String password;

    @Value("${DB_HOST:}")
    private String dbHost;

    @Value("${DB_PORT:5432}")
    private String dbPort;

    @Value("${DB_NAME:}")
    private String dbName;

    @Bean
    @Primary
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();

        String url = datasourceUrl;
        String user = username;
        String pass = password;

        // If cloud injected individual parameters (e.g. Render Blueprint)
        if (dbHost != null && !dbHost.trim().isEmpty()) {
            String port = (dbPort != null && !dbPort.trim().isEmpty()) ? dbPort.trim() : "5432";
            String db = (dbName != null && !dbName.trim().isEmpty()) ? dbName.trim() : "library_db";
            url = "jdbc:postgresql://" + dbHost.trim() + ":" + port + "/" + db;
        } else if (url != null && (url.startsWith("postgres://") || url.startsWith("postgresql://"))) {
            // If cloud injected standard connection string (e.g. Render / Heroku / Railway)
            try {
                URI uri = new URI(url);
                String userInfo = uri.getUserInfo();
                if (userInfo != null && userInfo.contains(":")) {
                    String[] parts = userInfo.split(":", 2);
                    user = parts[0];
                    pass = parts[1];
                }
                int port = uri.getPort() == -1 ? 5432 : uri.getPort();
                url = "jdbc:postgresql://" + uri.getHost() + ":" + port + uri.getPath();
            } catch (Exception ignored) {
                if (!url.startsWith("jdbc:")) {
                    url = "jdbc:" + url;
                }
            }
        }

        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(pass);
        config.setDriverClassName("org.postgresql.Driver");

        // Sensible connection pool defaults for cloud free-tier environments
        config.setMaximumPoolSize(5);
        config.setMinimumIdle(1);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);

        return new HikariDataSource(config);
    }
}
