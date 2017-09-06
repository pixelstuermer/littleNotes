package com.github.pixelstuermer.littleNotes.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DbStats extends Stats {

   private String db;
   private int collections;
   private int indexes;
   private double dataSize;

}
