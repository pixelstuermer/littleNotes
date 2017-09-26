package com.github.pixelstuermer.littleNotes.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
@RequestMapping( value = "/download" )
@Api( description = "Downloads notes documents" )
public class DownloadController {

   @Autowired
   MongoTemplate mongoTemplate;

   @Autowired
   Collection collection;

   @RequestMapping( method = RequestMethod.GET, value = "/all/json", produces = "application/json" )
   @ApiOperation( value = "Downloads all note documents as JSON" )
   public ResponseEntity<List<NoteEntry>> downloadAllJson( HttpServletResponse response ) {
      // set response headers
      String filename = "all-notes-" + new Date().getTime();
      response.addHeader( "Content-disposition", "attachment;filename=" + filename + ".json" );
      response.setContentType( "application/json" );

      // run mongo query
      DBCursor cursor = mongoTemplate.getCollection( collection.getCollectionName() ).find();
      List<NoteEntry> entryList = new ArrayList<>();
      while ( cursor.hasNext() ) {
         BasicDBObject document = (BasicDBObject) cursor.next();
         NoteEntry noteEntry = mongoTemplate.getConverter().read( NoteEntry.class, document );
         entryList.add( noteEntry );
      }

      // serve download
      return ResponseEntity.ok().body( entryList );
   }

}
