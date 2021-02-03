package br.com.felipe.login.config;

import lombok.NonNull;
import lombok.extern.java.Log;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutor;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.concurrent.Executor;


@Configuration
@Log
public class SpringAsyncConfig implements AsyncConfigurer {

	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor poolExecutor = new ThreadPoolTaskExecutor();
	    poolExecutor.setTaskDecorator(new ContextCopyingDecorator());
	    poolExecutor.setCorePoolSize(50);
	    poolExecutor.setMaxPoolSize(100);
	    poolExecutor.setQueueCapacity(1000);
	    poolExecutor.setThreadNamePrefix("login-service-pool");
	    poolExecutor.initialize();
        return new DelegatingSecurityContextExecutor(poolExecutor);
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		log.info("---AsyncUncaughtExceptionHandler---");
		return null;
	}

	static class ContextCopyingDecorator implements TaskDecorator {
		@NonNull
		@Override
		public Runnable decorate(@NonNull Runnable runnable) {
			RequestAttributes context =
					RequestContextHolder.currentRequestAttributes();
			return () -> {
				try {
					RequestContextHolder.setRequestAttributes(context);
					runnable.run();
				} finally {
					RequestContextHolder.resetRequestAttributes();
				}
			};
		}
	}
}
