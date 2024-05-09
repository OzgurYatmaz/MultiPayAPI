/**
 * This package contains classes for project configurations.
 */
package com.multipay.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * This class for creating custom beans to be Autowired.
 * 
 * @see /src/main/resources/application-dev.properties
 * 
 * @author Ozgur Yatmaz
 * @version 1.0.0
 * @since 2024-05-08
 * 
 */
@Configuration
public class AppConfig {

	
	/**
	 * 
	 * Creates bean of message source object which reads messages from messages.properties file.
	 * 
	 * @see /src/main/resources/messages/messages.properties
	 * 
	 * @return MessageSource object to be Autowired	 
	 * 
	 */
	@Bean
	public ResourceBundleMessageSource messageSource() {

		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasenames("messages/messages");
		source.setUseCodeAsDefaultMessage(true);

		return source;
	}
}
