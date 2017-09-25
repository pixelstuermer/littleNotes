package com.github.pixelstuermer.littleNotes.controllers;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pixelstuermer.littleNotes.models.Collection;
import com.mongodb.BasicDBList;
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
   public void downloadAllJson( HttpServletResponse response ) {
      // set response headers
      String filename = "all-notes-" + new Date().getTime();
      response.addHeader( "Content-disposition", "attachment;filename=" + filename + ".json" );
      response.setContentType( "application/json" );

      // run mongo query
      DBCursor cursor = mongoTemplate.getCollection( collection.getCollectionName() ).find();
      BasicDBList docsList = new BasicDBList();
      while ( cursor.hasNext() ) {
         BasicDBObject document = (BasicDBObject) cursor.next();
         docsList.add( document );
      }

      // write
      try {
         response.getOutputStream().write( docsList.toString().getBytes() );
         response.getOutputStream().flush();
      }
      catch ( Exception e ) {
         e.printStackTrace();
      }
   }

}
