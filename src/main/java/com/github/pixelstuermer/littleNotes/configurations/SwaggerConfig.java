package com.github.pixelstuermer.littleNotes.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

   @Value( "${project.version}" )
   private String projectVersion;

   @Value( "${project.name}" )
   private String projectName;

   @Value( "${project.description}" )
   private String projectDescription;

   @Value( "${project.author}" )
   private String projectAuthor;

   @Value( "${project.author.url}" )
   private String projectAuthorUrl;

   @Value( "${project.license}" )
   private String projectLicense;

   @Value( "${project.license.url}" )
   private String projectLicenseUrl;

   @Bean
   public Docket swaggerApi() {
      return new Docket( DocumentationType.SWAGGER_2 ).select()
         .apis( RequestHandlerSelectors.basePackage( "com.github.pixelstuermer.littleNotes.controllers" ) )
         .paths( PathSelectors.any() )
         .build()
         .apiInfo( getSwaggerMetaData() );
   }

   @SuppressWarnings( "deprecation" )
   private ApiInfo getSwaggerMetaData() {
      ApiInfo apiInfo = new ApiInfo( projectName, projectDescription, projectVersion, projectLicenseUrl,
         projectAuthorUrl, projectLicense, projectLicenseUrl );
      return apiInfo;
   }

}
