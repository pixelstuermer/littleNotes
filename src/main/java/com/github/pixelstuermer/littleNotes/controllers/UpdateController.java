package com.github.pixelstuermer.littleNotes.controllers;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pixelstuermer.littleNotes.models.Collection;
import com.github.pixelstuermer.littleNotes.models.UpdateResult;
import com.mongodb.BasicDBObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping( value = "/update" )
@Api( description = "Updates notes" )
public class UpdateController {

   @Autowired
   MongoTemplate mongoTemplate;

   @Autowired
   Collection collection;

   @RequestMapping( method = RequestMethod.PUT, value = "/update" )
   @ApiOperation( value = "Updates a note document" )
   public ResponseEntity<UpdateResult> deleteObjectIdNotes(
      @RequestHeader( value = "id", required = true ) String id,
      @RequestHeader( value = "note", required = true ) String note ) {
      // get old document
      BasicDBObject query = new BasicDBObject( "_id", new ObjectId( id ) );

      // set current Date
      Date date = new Date();

      // prepare new document
      BasicDBObject changes = new BasicDBObject();
      changes.put( "payload.note", note );
      changes.put( "metaData.changed", date );

      // execute mongo update query
      BasicDBObject update = new BasicDBObject();
      update.append( "$set", changes );
      mongoTemplate.getCollection( collection.getCollectionName() ).update( query, update );

      return ResponseEntity.ok().body( UpdateResult.builder().id( id )
         .changed( date.toString() ).build() );
   }

}
