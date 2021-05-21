package com.youx.demo.config;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TextEncoder {
	
	@Bean
	public BasicTextEncryptor bte() {
		BasicTextEncryptor bte = new BasicTextEncryptor();

		bte.setPassword("asjdmnkasndasdsa56d1a5s1d515ad61d2d2!@#@!#@!ASDAD!@D@!Dasd2!D213-1-939asd");
		
		return bte;
	}
}
