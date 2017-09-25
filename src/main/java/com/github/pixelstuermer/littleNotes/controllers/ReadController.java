package com.github.pixelstuermer.littleNotes.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pixelstuermer.littleNotes.models.Collection;
import com.github.pixelstuermer.littleNotes.models.NoteEntry;
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
   public ResponseEntity<ArrayList<NoteEntry>> getAllNotes() {
      DBCursor cursor = mongoTemplate.getCollection( collection.getCollectionName() ).find();
      ArrayList<NoteEntry> notesList = new ArrayList<>();
      while ( cursor.hasNext() ) {
         BasicDBObject document = (BasicDBObject) cursor.next();
         NoteEntry noteEntry = mongoTemplate.getConverter().read( NoteEntry.class, document );
         notesList.add( noteEntry );
      }
      return ResponseEntity.ok().body( notesList );
   }

   @RequestMapping( method = RequestMethod.GET, value = "/author" )
   @ApiOperation( value = "Shows notes according to an author" )
   public ResponseEntity<ArrayList<NoteEntry>> getAuthorNotes(
      @RequestHeader( value = "author", required = true ) String author ) {
      // prepare mongo query
      BasicDBObject query = new BasicDBObject( "payload.author", author );
      DBCursor cursor = mongoTemplate.getCollection( collection.getCollectionName() ).find( query );

      ArrayList<NoteEntry> notesList = new ArrayList<>();
      while ( cursor.hasNext() ) {
         BasicDBObject document = (BasicDBObject) cursor.next();
         NoteEntry noteEntry = mongoTemplate.getConverter().read( NoteEntry.class, document );
         notesList.add( noteEntry );
      }
      return ResponseEntity.ok().body( notesList );
   }

}
