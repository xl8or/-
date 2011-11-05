package org.apache.commons.io;

import java.io.File;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.Collection;
import java.util.Vector;
import org.apache.commons.io.FileDeleteStrategy;

public class FileCleaningTracker {

   volatile boolean exitWhenFinished;
   ReferenceQueue q;
   Thread reaper;
   final Collection trackers;


   public FileCleaningTracker() {
      ReferenceQueue var1 = new ReferenceQueue();
      this.q = var1;
      Vector var2 = new Vector();
      this.trackers = var2;
      this.exitWhenFinished = (boolean)0;
   }

   private void addTracker(String var1, Object var2, FileDeleteStrategy var3) {
      synchronized(this){}

      try {
         if(this.exitWhenFinished) {
            throw new IllegalStateException("No new trackers can be added once exitWhenFinished() is called");
         }

         if(this.reaper == null) {
            FileCleaningTracker.Reaper var5 = new FileCleaningTracker.Reaper();
            this.reaper = var5;
            this.reaper.start();
         }

         Collection var6 = this.trackers;
         ReferenceQueue var7 = this.q;
         FileCleaningTracker.Tracker var8 = new FileCleaningTracker.Tracker(var1, var3, var2, var7);
         var6.add(var8);
      } finally {
         ;
      }

   }

   public void exitWhenFinished() {
      // $FF: Couldn't be decompiled
   }

   public int getTrackCount() {
      return this.trackers.size();
   }

   public void track(File var1, Object var2) {
      FileDeleteStrategy var3 = (FileDeleteStrategy)false;
      this.track(var1, var2, var3);
   }

   public void track(File var1, Object var2, FileDeleteStrategy var3) {
      if(var1 == null) {
         throw new NullPointerException("The file must not be null");
      } else {
         String var4 = var1.getPath();
         this.addTracker(var4, var2, var3);
      }
   }

   public void track(String var1, Object var2) {
      FileDeleteStrategy var3 = (FileDeleteStrategy)false;
      this.track(var1, var2, var3);
   }

   public void track(String var1, Object var2, FileDeleteStrategy var3) {
      if(var1 == null) {
         throw new NullPointerException("The path must not be null");
      } else {
         this.addTracker(var1, var2, var3);
      }
   }

   private static final class Tracker extends PhantomReference {

      private final FileDeleteStrategy deleteStrategy;
      private final String path;


      Tracker(String var1, FileDeleteStrategy var2, Object var3, ReferenceQueue var4) {
         super(var3, var4);
         this.path = var1;
         FileDeleteStrategy var5;
         if(var2 == null) {
            var5 = FileDeleteStrategy.NORMAL;
         } else {
            var5 = var2;
         }

         this.deleteStrategy = var5;
      }

      public boolean delete() {
         FileDeleteStrategy var1 = this.deleteStrategy;
         String var2 = this.path;
         File var3 = new File(var2);
         return var1.deleteQuietly(var3);
      }
   }

   private final class Reaper extends Thread {

      Reaper() {
         super("File Reaper");
         this.setPriority(10);
         this.setDaemon((boolean)1);
      }

      public void run() {
         while(!FileCleaningTracker.this.exitWhenFinished || FileCleaningTracker.this.trackers.size() > 0) {
            FileCleaningTracker.Tracker var1;
            try {
               var1 = (FileCleaningTracker.Tracker)FileCleaningTracker.this.q.remove();
            } catch (Exception var5) {
               continue;
            }

            if(var1 != null) {
               boolean var2 = var1.delete();
               var1.clear();
               FileCleaningTracker.this.trackers.remove(var1);
            }
         }

      }
   }
}
