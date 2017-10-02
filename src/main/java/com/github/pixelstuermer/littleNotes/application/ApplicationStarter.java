package com.github.pixelstuermer.littleNotes.application;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan( basePackages = "com.github.pixelstuermer.littleNotes" )
public class ApplicationStarter {

   @Autowired
   Logger logger;

   public static void main( String[] args ) {
      SpringApplication.run( ApplicationStarter.class, args );
   }

}
