package espol.edu.ec.lpbike.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket apiDocket(SwaggerConfigProperties swaggerConfigProperties) {
		Docket docket =  new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage(swaggerConfigProperties.getSwaggerPackage()))
	            .paths(PathSelectors.any())
	            .build()
	            .apiInfo(apiEndPointsInfo(swaggerConfigProperties));;
	       
		return docket;
	       
	}
	
	private ApiInfo apiEndPointsInfo(SwaggerConfigProperties swaggerConfigProperties) {
        return new ApiInfoBuilder().title(swaggerConfigProperties.getTitulo())
            .description(swaggerConfigProperties.getDescripcion())
            .contact(new Contact(swaggerConfigProperties.getContacto(), swaggerConfigProperties.getPaginaWeb(), "ejemplo@example.com"))
            .version(swaggerConfigProperties.getVersion())
            .build();
    }
}
