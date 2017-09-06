package com.github.pixelstuermer.littleNotes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pixelstuermer.littleNotes.models.Collection;
import com.github.pixelstuermer.littleNotes.models.CollectionStats;
import com.github.pixelstuermer.littleNotes.models.Counter;
import com.github.pixelstuermer.littleNotes.models.DbStats;
import com.mongodb.BasicDBObject;

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

   @RequestMapping( method = RequestMethod.GET, value = "/db" )
   @ApiOperation( value = "Shows some stats of the database" )
   public ResponseEntity<DbStats> getDbStats() {
      BasicDBObject document = mongoTemplate.getDb().getStats();
      DbStats stats = mongoTemplate.getConverter().read( DbStats.class, document );
      return ResponseEntity.ok().body( stats );
   }

   @RequestMapping( method = RequestMethod.GET, value = "/collection" )
   @ApiOperation( value = "Shows some stats of the collection" )
   public ResponseEntity<CollectionStats> getCollectionStats() {
      BasicDBObject document = mongoTemplate.getCollection( collection.getCollectionName() ).getStats();
      CollectionStats stats = mongoTemplate.getConverter().read( CollectionStats.class, document );
      return ResponseEntity.ok().body( stats );
   }

   @RequestMapping( method = RequestMethod.GET, value = "/collection/count" )
   @ApiOperation( value = "Shows the number of stored documents within the collection" )
   public ResponseEntity<Counter> getCollectionCount() {
      long count = mongoTemplate.getCollection( collection.getCollectionName() ).count();
      return ResponseEntity.ok().body( Counter.builder().documents( count ).build() );
   }

}
