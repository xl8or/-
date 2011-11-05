package org.apache.commons.io;

import java.io.File;
import org.apache.commons.io.FileCleaningTracker;
import org.apache.commons.io.FileDeleteStrategy;

public class FileCleaner {

   static final FileCleaningTracker theInstance = new FileCleaningTracker();


   public FileCleaner() {}

   public static void exitWhenFinished() {
      synchronized(FileCleaner.class){}

      try {
         theInstance.exitWhenFinished();
      } finally {
         ;
      }

   }

   public static FileCleaningTracker getInstance() {
      return theInstance;
   }

   public static int getTrackCount() {
      return theInstance.getTrackCount();
   }

   public static void track(File var0, Object var1) {
      theInstance.track(var0, var1);
   }

   public static void track(File var0, Object var1, FileDeleteStrategy var2) {
      theInstance.track(var0, var1, var2);
   }

   public static void track(String var0, Object var1) {
      theInstance.track(var0, var1);
   }

   public static void track(String var0, Object var1, FileDeleteStrategy var2) {
      theInstance.track(var0, var1, var2);
   }
}
