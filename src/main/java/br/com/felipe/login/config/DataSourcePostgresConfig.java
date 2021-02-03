package br.com.felipe.login.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Danilo Silva
 **/
@AllArgsConstructor
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "postgresServerEntityManagerFactory",
    transactionManagerRef = "postgresTransactionManager",
    basePackages = {"com.thesolution.projects.backend.auth.repository"}
)
public class DataSourcePostgresConfig {

    private final Environment environment;

    @Primary
    @Bean(name = "postgres")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource db2saDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "jpa")
    @ConfigurationProperties(prefix = "spring.jpa")
    public JpaVendorAdapter db2saJpa() {
        return new HibernateJpaVendorAdapter();
    }

    @Primary
    @Bean(name = "postgresServerEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                       @Qualifier("postgres") DataSource dataSource,
                                                                       @Qualifier("jpa") JpaVendorAdapter jpaVendorAdapter) {
        return builder
            .dataSource(dataSource)
            .packages("com.thesolution.projects.backend.auth.domain")
            .properties(jpaProperties(jpaVendorAdapter))
            .persistenceUnit("postgresqlPU")
            .build();
    }

    private Map<String, Object> jpaProperties(JpaVendorAdapter jpaVendorAdapter) {
        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class.getName());
        props.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
        props.put("hibernate.dialect", environment.getProperty("spring.jpa.properties.hibernate.dialect"));
        props.put("hibernate.jdbc.lob.non_contextual_creation", environment.getProperty("spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation"));
        props.put("hibernate.show_sql", jpaVendorAdapter.getJpaPropertyMap().get("hibernate.show_sql"));
        props.put("hibernate.format_sql", true);

        return props;
    }

    @Primary
    @Bean(name = "postgresTransactionManager")
    public PlatformTransactionManager admServerTransactionManager(@Qualifier("postgresServerEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
