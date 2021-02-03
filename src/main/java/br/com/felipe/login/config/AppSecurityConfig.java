package br.com.felipe.login.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Configuration
@ConfigurationProperties(prefix = "app.security")
@Getter
@Setter
@ToString
public class AppSecurityConfig {
	private AppPermitRequestsConfig permitRequests;

	@Getter
	@Setter
	@ToString
	public static class AppPermitRequestsConfig {
		private AppMatchersPermitRequestsConfig framework;
		private AppMatchersPermitRequestsConfig business;
		private List<String> matchersAll;
		private List<String> matchersGet;

		public List<String> builderPermitAll() {
			List<String> result = new ArrayList<>();
			if (matchersAll != null) {
			  result.addAll(matchersAll);
			}
			if (framework != null && framework.matchersAll != null) {
				  result.addAll(framework.matchersAll);
			}
			if (business != null && business.matchersAll != null) {
				  result.addAll(business.matchersAll);
			}
			return result;
		}

		public List<String> builderPermitGet() {
			List<String> result = new ArrayList<>();
			if (matchersGet != null) {
			  result.addAll(matchersGet);
			}
			if (framework != null && framework.matchersGet != null) {
				  result.addAll(framework.matchersGet);
			}
			if (business != null && business.matchersGet != null) {
				  result.addAll(business.matchersGet);
			}
			return result;
		}


		@Getter
		@Setter
		@ToString
		public static class AppMatchersPermitRequestsConfig {
			private List<String> matchersAll;
			private List<String> matchersGet;
		}
	}

}
