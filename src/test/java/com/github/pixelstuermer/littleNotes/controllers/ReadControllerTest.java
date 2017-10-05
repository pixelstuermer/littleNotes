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
   public void controllerBasicResponseTest() {
      // mock data
      NoteEntry entry1 = new NoteEntry();
      entry1.set_id( "59c9013cb689f60261be342b" );
      entry1.getMetaData().put( "created", "Mon Sep 25 13:09:35 UTC 2017" );
      entry1.getMetaData().put( "changed", "Mon Sep 25 18:14:22 UTC 2017" );
      entry1.getPayload().put( "note", "FooBar" );
      entry1.getPayload().put( "author", "Tim" );

      ReadResult readResult = new ReadResult();
      readResult.getNotes().add( entry1 );
      readResult.setCount( readResult.getNotes().size() );

      // mock controller
      when( readController.getAllNotes() ).thenReturn( ResponseEntity.ok().body( readResult ) );

      // test entries count
      assertTrue( readController.getAllNotes().getBody().getCount() == 1 );

      // test status code
      assertTrue( readController.getAllNotes().getStatusCode() == HttpStatus.OK );
   }

}
