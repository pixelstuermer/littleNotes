package com.github.pixelstuermer.littleNotes.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CollectionStats extends Stats {

   private String ns;
   private int count;
   private int nindexes;
   private double size;
   private double totalIndexSize;

}
