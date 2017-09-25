package com.github.pixelstuermer.littleNotes.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
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
      ArrayList<NoteEntry> notesList = new ArrayList<>();
      DBCursor cursor = mongoTemplate.getCollection( collection.getCollectionName() ).find();
      while ( cursor.hasNext() ) {
         BasicDBObject document = (BasicDBObject) cursor.next();
         NoteEntry noteEntry = mongoTemplate.getConverter().read( NoteEntry.class, document );
         notesList.add( noteEntry );
      }
      return ResponseEntity.ok().body( notesList );
   }

}
