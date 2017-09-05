package com.github.pixelstuermer.littleNotes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pixelstuermer.littleNotes.models.Collection;
import com.github.pixelstuermer.littleNotes.models.Counter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping( value = "/stats" )
@Api( "Lists stats for the \"Notes\" collection" )
public class StatsController {

   @Autowired
   MongoTemplate mongoTemplate;

   @Autowired
   Collection collection;

   @RequestMapping( method = RequestMethod.GET, value = "/count" )
   @ApiOperation( value = "Shows the number of stored documents" )
   public ResponseEntity<Counter> countDocuments() {
      long count = mongoTemplate.getCollection( collection.getCollectionName() ).count();
      return ResponseEntity.ok().body( Counter.builder().documents( count ).build() );
   }

}
