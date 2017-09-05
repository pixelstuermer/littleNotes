package com.github.pixelstuermer.littleNotes.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.Mongo;

@Configuration
public class MongoDbConfig {

   @Bean
   @SuppressWarnings( "deprecation" )
   public Mongo mongo( @Value( "${spring.data.mongodb.uri}" ) String uri ) {
      return new Mongo( uri );
   }

}
