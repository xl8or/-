package com.google.android.finsky.model;


public class Track {

   public String album;
   public String artist;
   public String docId;
   public String length;
   public Track.TrackMode mode;
   public String price;
   public String title;
   public int trackNo;
   public String url;
   public int year;


   public Track() {}

   public static enum TrackMode {

      // $FF: synthetic field
      private static final Track.TrackMode[] $VALUES;
      LOADING("LOADING", 1),
      PAUSE("PAUSE", 3),
      PLAYING("PLAYING", 2),
      READY("READY", 0);


      static {
         Track.TrackMode[] var0 = new Track.TrackMode[4];
         Track.TrackMode var1 = READY;
         var0[0] = var1;
         Track.TrackMode var2 = LOADING;
         var0[1] = var2;
         Track.TrackMode var3 = PLAYING;
         var0[2] = var3;
         Track.TrackMode var4 = PAUSE;
         var0[3] = var4;
         $VALUES = var0;
      }

      private TrackMode(String var1, int var2) {}
   }
}
