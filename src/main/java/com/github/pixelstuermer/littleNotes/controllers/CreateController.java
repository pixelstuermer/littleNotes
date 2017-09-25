package com.github.pixelstuermer.littleNotes.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pixelstuermer.littleNotes.models.Collection;
import com.github.pixelstuermer.littleNotes.models.CreateResult;
import com.mongodb.BasicDBObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping( value = "/create" )
@Api( description = "Creates notes" )
public class CreateController {

   @Autowired
   MongoTemplate mongoTemplate;

   @Autowired
   Collection collection;

   @RequestMapping( method = RequestMethod.POST, value = "/new" )
   @ApiOperation( value = "Creates a new note document" )
   public ResponseEntity<CreateResult> deleteObjectIdNotes(
      @RequestHeader( value = "author", required = true ) String author,
      @RequestHeader( value = "note", required = true ) String note ) {
      // set current Date
      Date date = new Date();

      // prepare mongo query
      BasicDBObject document = new BasicDBObject();
      BasicDBObject metaData = new BasicDBObject();
      BasicDBObject payload = new BasicDBObject();

      document.put( "metaData", metaData );
      metaData.put( "created", date );
      metaData.put( "changed", date );

      document.put( "payload", payload );
      payload.put( "author", author );
      payload.put( "note", note );

      try {
         mongoTemplate.getCollection( collection.getCollectionName() ).insert( document );
         return ResponseEntity.ok().body( CreateResult.builder().success( true )
            .created( date.toString() ).id( document.getString( "_id" ) ).build() );
      }
      catch ( Exception e ) {
         return ResponseEntity.badRequest().body( CreateResult.builder().success( false )
            .created( null ).id( null ).build() );
      }
   }

}
