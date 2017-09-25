package com.github.pixelstuermer.littleNotes.controllers;

import java.util.regex.Pattern;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pixelstuermer.littleNotes.models.Collection;
import com.github.pixelstuermer.littleNotes.models.NoteEntry;
import com.github.pixelstuermer.littleNotes.models.ReadResult;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping( value = "/read" )
@Api( description = "Reads notes" )
public class ReadController {

   @Autowired
   MongoTemplate mongoTemplate;

   @Autowired
   Collection collection;

   @RequestMapping( method = RequestMethod.GET, value = "/all" )
   @ApiOperation( value = "Shows all notes" )
   public ResponseEntity<ReadResult> getAllNotes() {
      // prepare mongo query
      DBCursor cursor = mongoTemplate.getCollection( collection.getCollectionName() ).find();

      // prepare readResult
      ReadResult readResult = new ReadResult();
      readResult.setCount( cursor.count() );

      while ( cursor.hasNext() ) {
         BasicDBObject document = (BasicDBObject) cursor.next();
         NoteEntry noteEntry = mongoTemplate.getConverter().read( NoteEntry.class, document );
         readResult.getNotes().add( noteEntry );
      }
      return ResponseEntity.ok().body( readResult );
   }

   @RequestMapping( method = RequestMethod.GET, value = "/author" )
   @ApiOperation( value = "Shows notes according to an author" )
   public ResponseEntity<ReadResult> getAuthorNotes(
      @RequestHeader( value = "author", required = true ) String author ) {
      // prepare mongo query
      BasicDBObject query = new BasicDBObject( "payload.author", author );
      DBCursor cursor = mongoTemplate.getCollection( collection.getCollectionName() ).find( query );

      // prepare readResult
      ReadResult readResult = new ReadResult();
      readResult.setCount( cursor.count() );

      while ( cursor.hasNext() ) {
         BasicDBObject document = (BasicDBObject) cursor.next();
         NoteEntry noteEntry = mongoTemplate.getConverter().read( NoteEntry.class, document );
         readResult.getNotes().add( noteEntry );
      }
      return ResponseEntity.ok().body( readResult );
   }

   @RequestMapping( method = RequestMethod.GET, value = "/id" )
   @ApiOperation( value = "Shows notes according to an ObjectId" )
   public ResponseEntity<NoteEntry> getObjectIdNotes(
      @RequestHeader( value = "id", required = true ) String id ) {
      // prepare mongo query
      BasicDBObject query = new BasicDBObject( "_id", new ObjectId( id ) );
      BasicDBObject document = (BasicDBObject) mongoTemplate.getCollection( collection.getCollectionName() )
         .findOne( query );

      NoteEntry noteEntry = mongoTemplate.getConverter().read( NoteEntry.class, document );
      return ResponseEntity.ok().body( noteEntry );
   }

   @RequestMapping( method = RequestMethod.GET, value = "/keyword" )
   @ApiOperation( value = "Shows notes according to a keyword" )
   public ResponseEntity<ReadResult> getKeywordNotes(
      @RequestHeader( value = "keyword", required = true ) String keyword ) {
      // prepare mongo query
      BasicDBObject query = new BasicDBObject( "payload.note", Pattern.compile( keyword ) );
      DBCursor cursor = mongoTemplate.getCollection( collection.getCollectionName() ).find( query );

      // prepare readResult
      ReadResult readResult = new ReadResult();
      readResult.setCount( cursor.count() );

      while ( cursor.hasNext() ) {
         BasicDBObject document = (BasicDBObject) cursor.next();
         NoteEntry noteEntry = mongoTemplate.getConverter().read( NoteEntry.class, document );
         readResult.getNotes().add( noteEntry );
      }
      return ResponseEntity.ok().body( readResult );
   }

}
