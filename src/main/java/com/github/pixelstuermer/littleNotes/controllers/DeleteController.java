package com.github.pixelstuermer.littleNotes.controllers;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pixelstuermer.littleNotes.models.Collection;
import com.github.pixelstuermer.littleNotes.models.DeleteResult;
import com.github.pixelstuermer.littleNotes.utils.Roles;
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

   @Autowired
   Logger logger;

   @RequestMapping( method = RequestMethod.DELETE, value = "/id" )
   @PreAuthorize( "hasRole('" + Roles.ADMIN + "')" )
   @ApiOperation( value = "Deletes a note according to its ObjectId" )
   public ResponseEntity<DeleteResult> deleteObjectIdNotes(
      @RequestHeader( value = "id", required = true ) String id ) {
      try {
         // prepare mongo query
         BasicDBObject query = new BasicDBObject( "_id", new ObjectId( id ) );
         mongoTemplate.getCollection( collection.getCollectionName() ).remove( query );
         logger.info( "ID {} deleted", id );
         return ResponseEntity.ok().body( DeleteResult.builder().success( true )
            .message( "note " + id + " deleted" ).build() );
      }
      catch ( Exception e ) {
         logger.info( "Tried to delete ID {}", id );
         return ResponseEntity.badRequest().body( DeleteResult.builder().success( false )
            .message( "note " + id + " not deleted" ).build() );
      }
   }

   @RequestMapping( method = RequestMethod.DELETE, value = "/author" )
   @PreAuthorize( "hasRole('" + Roles.ADMIN + "')" )
   @ApiOperation( value = "Deletes notes according to their author" )
   public ResponseEntity<DeleteResult> deleteAuthorNotes(
      @RequestHeader( value = "author", required = true ) String author ) {
      // prepare mongo query
      BasicDBObject query = new BasicDBObject( "payload.author", author );

      mongoTemplate.getCollection( collection.getCollectionName() ).remove( query );
      logger.info( "Deleted all notes from {}", author );
      return ResponseEntity.ok().body( DeleteResult.builder().success( true )
         .message( "notes from " + author + " deleted" ).build() );
   }

   @RequestMapping( method = RequestMethod.DELETE, value = "/all" )
   @PreAuthorize( "hasRole('" + Roles.ADMIN + "') and hasRole('" + Roles.USER + "')" )
   @ApiOperation( value = "Deletes all notes" )
   public ResponseEntity<String> deleteAllNotes() {
      mongoTemplate.getCollection( collection.getCollectionName() ).remove( new BasicDBObject() );
      logger.info( "Deleted absolutely all notes from all authors" );
      return ResponseEntity.ok().build();
   }

}
