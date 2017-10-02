package com.github.pixelstuermer.littleNotes.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity( prePostEnabled = true )
public class SecurityConfig extends WebSecurityConfigurerAdapter {

   @Autowired
   public void configureGlobalSecurity( AuthenticationManagerBuilder auth,
      @Value( "${admin.password}" ) String adminPassword,
      @Value( "${user.password}" ) String userPassword,
      @Value( "${phil.password}" ) String philPassword ) throws Exception {
      auth.inMemoryAuthentication().withUser( "admin" ).password( adminPassword ).roles( "ADMIN" );
      auth.inMemoryAuthentication().withUser( "user" ).password( userPassword ).roles( "USER" );
      auth.inMemoryAuthentication().withUser( "phil" ).password( philPassword ).roles( "ADMIN", "USER" );
   }

   @Override
   protected void configure( final HttpSecurity http ) throws Exception {
      http.authorizeRequests()
         .anyRequest().permitAll()
         .and().httpBasic()
         .and().csrf().disable();
   }

}
