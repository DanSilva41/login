package br.com.felipe.login.security;

import br.com.felipe.login.config.AppSecurityConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import java.util.Arrays;


@Configuration
@EnableResourceServer
@EnableWebSecurity
@Slf4j
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
    AppSecurityConfig appSecurityConfig;

    @Autowired
    @Qualifier("appUserDetailsService")
    private UserDetailsService userDetailsService;


    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
		String[] aPermitAll = appSecurityConfig.getPermitRequests().builderPermitAll().toArray(new String[0]);
		String[] aPermitGet = appSecurityConfig.getPermitRequests().builderPermitGet().toArray(new String[0]);
		log.info("Configuring authorizeRequests authorizing ALL request to with filter "+Arrays.toString(aPermitAll));
		log.info("Configuring authorizeRequests authorizing GET request to with filter "+Arrays.toString(aPermitGet));
        http.csrf().disable()
        .authorizeRequests()
		.antMatchers(aPermitAll).permitAll()
            .antMatchers(HttpMethod.GET, aPermitGet).permitAll()
		.anyRequest().authenticated();
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OAuth2FeignRequestInterceptor feignRequestInterceptor(
        OAuth2ClientContext oAuth2ClientContext, OAuth2ProtectedResourceDetails resource) {
        return new OAuth2FeignRequestInterceptor(oAuth2ClientContext, resource);
    }


}
