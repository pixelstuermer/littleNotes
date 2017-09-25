package com.github.pixelstuermer.littleNotes.controllers;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pixelstuermer.littleNotes.models.Collection;
import com.github.pixelstuermer.littleNotes.models.DeleteResult;
import com.mongodb.BasicDBObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping( value = "/delete" )
@Api( description = "Deletes notes" )
public class DeleteController {

   @Autowired
   MongoTemplate mongoTemplate;

   @Autowired
   Collection collection;

   @RequestMapping( method = RequestMethod.DELETE, value = "/id" )
   @ApiOperation( value = "Deletes a note according to its ObjectId" )
   public ResponseEntity<DeleteResult> deleteObjectIdNotes(
      @RequestHeader( value = "id", required = true ) String id ) {
      try {
         // prepare mongo query
         BasicDBObject query = new BasicDBObject( "_id", new ObjectId( id ) );
         mongoTemplate.getCollection( collection.getCollectionName() ).remove( query );
         return ResponseEntity.ok().body( DeleteResult.builder().success( true )
            .message( "note " + id + " deleted" ).build() );
      }
      catch ( Exception e ) {
         return ResponseEntity.badRequest().body( DeleteResult.builder().success( false )
            .message( "note " + id + " not deleted" ).build() );
      }
   }

   @RequestMapping( method = RequestMethod.DELETE, value = "/author" )
   @ApiOperation( value = "Deletes notes according to their author" )
   public ResponseEntity<DeleteResult> deleteAuthorNotes(
      @RequestHeader( value = "author", required = true ) String author ) {
      // prepare mongo query
      BasicDBObject query = new BasicDBObject( "payload.author", author );
      mongoTemplate.getCollection( collection.getCollectionName() ).remove( query );
      return ResponseEntity.ok().body( DeleteResult.builder().success( true )
         .message( "notes from " + author + " deleted" ).build() );
   }

}
