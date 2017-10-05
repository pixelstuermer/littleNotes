package com.github.pixelstuermer.littleNotes.controllers;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.github.pixelstuermer.littleNotes.models.NoteEntry;
import com.github.pixelstuermer.littleNotes.models.ReadResult;

@RunWith( MockitoJUnitRunner.class )
public class ReadControllerTest {

   @Mock
   private ReadController readController;

   @Test
   public void controllerInitializedCorrectly() {
      assertTrue( readController != null );
   }

   @Test
   public void basicResponseTest() {
      // mock data
      NoteEntry entry = new NoteEntry();
      entry.set_id( "59c9013cb689f60261be342b" );
      entry.getMetaData().put( "created", "Mon Sep 25 13:09:35 UTC 2017" );
      entry.getMetaData().put( "changed", "Mon Sep 25 18:14:22 UTC 2017" );
      entry.getPayload().put( "note", "FooBar" );
      entry.getPayload().put( "author", "Tim" );

      ReadResult readResult = new ReadResult();
      readResult.getNotes().add( entry );
      readResult.setCount( readResult.getNotes().size() );

      // mock controller
      when( readController.getAllNotes() ).thenReturn( ResponseEntity.ok().body( readResult ) );

      // test entries count
      assertTrue( readController.getAllNotes().getBody().getCount() == 1 );

      // test status code
      assertTrue( readController.getAllNotes().getStatusCode() == HttpStatus.OK );
   }

   @Test
   public void idGetterTest() {
      // mock data
      String id = "59c9013cb689f60261be342b";
      String note = "FooBar";
      String author = "Sarah";

      NoteEntry entry = new NoteEntry();
      entry.set_id( id );
      entry.getMetaData().put( "created", "Mon Sep 25 13:09:35 UTC 2017" );
      entry.getMetaData().put( "changed", "Mon Sep 25 18:14:22 UTC 2017" );
      entry.getPayload().put( "note", note );
      entry.getPayload().put( "author", author );

      // mock controller
      when( readController.getObjectIdNotes( id ) ).thenReturn( ResponseEntity.ok().body( entry ) );

      // test id equality
      assertTrue( readController.getObjectIdNotes( id ).getBody().get_id() == id );

      // test note entry
      assertTrue( readController.getObjectIdNotes( id ).getBody().getPayload().get( "note" ) == note );

      // test author name
      assertTrue( readController.getObjectIdNotes( id ).getBody().getPayload().get( "author" ) == author );

      // test status code
      assertTrue( readController.getObjectIdNotes( id ).getStatusCode() == HttpStatus.OK );
   }

}
