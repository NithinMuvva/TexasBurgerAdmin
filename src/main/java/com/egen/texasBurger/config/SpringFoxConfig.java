package com.egen.texasBurger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SpringFoxConfig {

	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.basePackage("com.egen.texasBurger"))              
          .paths(PathSelectors.any())                          
          .build().apiInfo(apiInfo());                                           
    }
	
	private ApiInfo apiInfo() {
		return new ApiInfo(
				"Texas Burger Admin Panel",
				"Spring Boot REST API for Texas Burger Admin App",
				"1.0.0",
				"Terms of service",
				new Contact("Nithin", "", "muvva.n@northeastern.edu"),
				"License of API", "API license URL", Collections.emptyList());
	}
	
	
}
