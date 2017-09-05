package com.github.pixelstuermer.littleNotes.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping( value = "/" )
@Api( "Test-Controller" )
public class TestController {

   @RequestMapping( method = RequestMethod.GET, value = "/" )
   @ApiOperation( value = "Controller to simply test the spring boot application" )
   public ResponseEntity<String> testIndex() {
      return ResponseEntity.ok().body( "Hello World!" );
   }

}
