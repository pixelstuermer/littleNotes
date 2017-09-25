package com.github.pixelstuermer.littleNotes.models;

import java.util.ArrayList;

import lombok.Data;

@Data
public class ReadResult {

   private int count;
   private ArrayList<NoteEntry> notes;

   public ReadResult() {
      notes = new ArrayList<>();
   }

}
