package br.com.felipe.login;

import feign.Logger;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.context.request.RequestContextListener;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
@EnableAsync
@EnableFeignClients
@EntityScan(basePackages = {"com.thesolution.projects.backend.auth.domain.security"})
@SpringBootApplication
public class LoginServiceApplication implements CommandLineRunner {

    @Autowired
    private ResourceServerProperties sso;
    @Autowired
    private Environment env;

    @Value("${server.port}")
    private String serverPort;
    @Value("${server.servlet.context-path}")
    private String contextPath;
    @Value("${spring.application.name}")
    private String applicationName;

    private static final String TOP_MESSAGE = "LOGIN SERVICE is running!";
    private static final String LINE = "----------------------------------------------------------------";

    public static void main(String[] args) {
        SpringApplication.run(LoginServiceApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Instant now = Instant.now();
        log.info("---> Started on " + ZonedDateTime.ofInstant(now, ZoneId.systemDefault()).toOffsetDateTime());
        log.info("Default Timezone: " + ZoneId.systemDefault() + " | OffsetDateTime:" + OffsetDateTime.now());
        log.info("UTC Time: " + ZonedDateTime.ofInstant(now, ZoneId.of("UTC")));
        log.info("São Paulo: " + ZonedDateTime.ofInstant(now, ZoneId.of("America/Sao_Paulo")));
        log.info("Java-Name: {}", System.getProperty("java.specification.name"));
        log.info("Java-Vendor: {}", System.getProperty("java.specification.vendor"));
        log.info("Java-Version: {}", System.getProperty("java.specification.version"));
        log.info("Java-Runtime-Version: {}", System.getProperty("java.runtime.version"));
        log.info("file.encoding: {}", System.getProperty("file.encoding"));

        log.debug("Using PublicKey \n{}", sso.getJwt().getKeyValue());
        log.info("spring.datasource.jdbc-url: " + env.getProperty("spring.datasource.jdbc-url"));
        log.info("spring.datasource.username: " + env.getProperty("spring.datasource.username"));

        StringBuilder infoMessage = this.generateMessageWithUrlsApplication();

        log.info("{}", infoMessage);
    }

    private StringBuilder generateMessageWithUrlsApplication() {
        StringBuilder infoMessage = new StringBuilder();

        String hostAddress;
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error("Não foi possível capturar o endereço HOST da aplicação!");
            return null;
        }

        infoMessage.append("*********************\n")
            .append(LINE)
            .append("\n\t")
            .append(TOP_MESSAGE)
            .append("\n\t")
            .append("Application: http://").append(hostAddress)
            .append(":").append(serverPort).append(contextPath)
            .append("\n\t")
            .append("Swagger: http://").append(hostAddress)
            .append(":").append(serverPort).append(contextPath)
            .append("/swagger-ui.html")
            .append("\n")
            .append(LINE);
        return infoMessage;
    }


    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.failOnEmptyBeans(false);
        return builder;
    }

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
