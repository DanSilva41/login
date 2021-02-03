package br.com.felipe.login.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Configuration
@ConfigurationProperties(prefix = "app")
@Getter
@Setter
@ToString
public class AppConfig {

	private String tipoAmbiente;

}
