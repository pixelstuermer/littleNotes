package com.github.pixelstuermer.littleNotes.utils;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UtilsTest {

   @Test
   public void testRoles() {
      // simply check the two existing roles
      assertTrue( Roles.class.getFields().length == 2 );
      assertTrue( Roles.ADMIN == "admin" );
      assertTrue( Roles.USER == "user" );
   }

}
