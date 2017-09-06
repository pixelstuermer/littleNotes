package com.github.pixelstuermer.littleNotes.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Stats {

   private double storageSize;
   private double avgObjSize;

}
