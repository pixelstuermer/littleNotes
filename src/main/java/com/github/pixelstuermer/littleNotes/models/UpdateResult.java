package com.github.pixelstuermer.littleNotes.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateResult {

   private String id;
   private String changed;

}
