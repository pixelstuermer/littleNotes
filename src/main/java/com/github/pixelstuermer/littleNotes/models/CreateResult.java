package com.github.pixelstuermer.littleNotes.models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateResult {

   private boolean success;
   private String id;
   private String created;

}
