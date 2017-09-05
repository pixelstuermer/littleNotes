package com.github.pixelstuermer.littleNotes.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.pixelstuermer.littleNotes.models.Collection;

@Configuration
public class CollectionConfig {

   @Bean
   public Collection setNotesCollection( @Value( "${collection.name}" ) String collectionName ) {
      return Collection.builder().collectionName( collectionName ).build();
   }

}
