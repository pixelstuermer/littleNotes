package com.github.pixelstuermer.littleNotes.models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeleteResult {

   private String message;
   private boolean success;

}
