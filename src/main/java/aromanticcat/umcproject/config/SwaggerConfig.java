package aromanticcat.umcproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {  // Swagger

    private static final String API_NAME = "UMC 5th 낭만고양이 API";
    private static final String API_VERSION = "0.0.1";
    private static final String API_DESCRIPTION = "낭만고양이 API 명세서";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
//                .securityContexts(Arrays.asList(securityContext())) // 추가
//                .securitySchemes(Arrays.asList(apiKey())) // 추가
                .select()
                .apis(RequestHandlerSelectors.basePackage("aromanticcat.umcproject"))  // Swagger를 적용할 클래스의 package명
                .paths(PathSelectors.any())  // 해당 package 하위에 있는 모든 url에 적용
                .build()
                .apiInfo(apiInfo());
    }

    public ApiInfo apiInfo() {  // API의 이름, 현재 버전, API에 대한 정보
        return new ApiInfoBuilder()
                .title(API_NAME)
                .version(API_VERSION)
                .description(API_DESCRIPTION)
                .build();
    }

//    // 추가
//    private SecurityContext securityContext() {
//        return SecurityContext.builder()
//                .securityReferences(defaultAuth())
//                .build();
//    }
//
//    // 추가
//    private List<SecurityReference> defaultAuth() {
//        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
//    }
//
//    // 추가
//    private ApiKey apiKey() {
//        return new ApiKey("Authorization", "Authorization", "header");
//    }
}
