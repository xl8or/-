package com.htc.android.mail;

import java.util.HashMap;
import java.util.Iterator;

public class MediaFile {

   public static final int FILE_TYPE_3GPP = 23;
   public static final int FILE_TYPE_3GPP2 = 24;
   public static final int FILE_TYPE_AAC = 8;
   public static final int FILE_TYPE_AMR = 4;
   public static final int FILE_TYPE_AWB = 5;
   public static final int FILE_TYPE_BMP = 34;
   public static final int FILE_TYPE_GIF = 32;
   public static final int FILE_TYPE_IMY = 13;
   public static final int FILE_TYPE_JPEG = 31;
   public static final int FILE_TYPE_M3U = 41;
   public static final int FILE_TYPE_M4A = 2;
   public static final int FILE_TYPE_M4V = 22;
   public static final int FILE_TYPE_MID = 11;
   public static final int FILE_TYPE_MP3 = 1;
   public static final int FILE_TYPE_MP4 = 21;
   public static final int FILE_TYPE_OGG = 7;
   public static final int FILE_TYPE_PLS = 42;
   public static final int FILE_TYPE_PNG = 33;
   public static final int FILE_TYPE_SMF = 12;
   public static final int FILE_TYPE_WAV = 3;
   public static final int FILE_TYPE_WBMP = 35;
   public static final int FILE_TYPE_WMA = 6;
   public static final int FILE_TYPE_WMV = 25;
   public static final int FILE_TYPE_WPL = 43;
   private static final int FIRST_AUDIO_FILE_TYPE = 1;
   private static final int FIRST_IMAGE_FILE_TYPE = 31;
   private static final int FIRST_MIDI_FILE_TYPE = 11;
   private static final int FIRST_PLAYLIST_FILE_TYPE = 41;
   private static final int FIRST_VIDEO_FILE_TYPE = 21;
   private static final int LAST_AUDIO_FILE_TYPE = 8;
   private static final int LAST_IMAGE_FILE_TYPE = 35;
   private static final int LAST_MIDI_FILE_TYPE = 13;
   private static final int LAST_PLAYLIST_FILE_TYPE = 43;
   private static final int LAST_VIDEO_FILE_TYPE = 25;
   public static final String UNKNOWN_STRING = "<unknown>";
   public static String sFileExtensions;
   private static HashMap<String, MediaFile.MediaFileType> sFileTypeMap = new HashMap();
   private static HashMap<String, Integer> sMimeTypeMap = new HashMap();


   static {
      addFileType("MP3", 1, "audio/mpeg");
      addFileType("M4A", 2, "audio/mp4");
      addFileType("WAV", 3, "audio/x-wav");
      addFileType("AMR", 4, "audio/amr");
      addFileType("AWB", 5, "audio/amr-wb");
      addFileType("WMA", 6, "audio/x-ms-wma");
      addFileType("OGG", 7, "application/ogg");
      addFileType("AAC", 8, "audio/aac");
      addFileType("MID", 11, "audio/midi");
      addFileType("XMF", 11, "audio/midi");
      addFileType("RTTTL", 11, "audio/midi");
      addFileType("SMF", 12, "audio/sp-midi");
      addFileType("IMY", 13, "audio/imelody");
      addFileType("MP4", 21, "video/mp4");
      addFileType("M4V", 22, "video/mp4");
      addFileType("3GP", 23, "video/3gpp");
      addFileType("3GPP", 23, "video/3gpp");
      addFileType("3G2", 24, "video/3gpp2");
      addFileType("3GPP2", 24, "video/3gpp2");
      addFileType("WMV", 25, "video/x-ms-wmv");
      addFileType("JPG", 31, "image/jpeg");
      addFileType("JPEG", 31, "image/jpeg");
      addFileType("GIF", 32, "image/gif");
      addFileType("PNG", 33, "image/png");
      addFileType("BMP", 34, "image/x-ms-bmp");
      addFileType("WBMP", 35, "image/vnd.wap.wbmp");
      addFileType("M3U", 41, "audio/x-mpegurl");
      addFileType("PLS", 42, "audio/x-scpls");
      addFileType("WPL", 43, "application/vnd.ms-wpl");
      StringBuilder var0 = new StringBuilder();
      Iterator var1 = sFileTypeMap.keySet().iterator();

      while(var1.hasNext()) {
         if(var0.length() > 0) {
            StringBuilder var2 = var0.append(',');
         }

         String var3 = (String)var1.next();
         var0.append(var3);
      }

      sFileExtensions = var0.toString();
   }

   public MediaFile() {}

   static void addFileType(String var0, int var1, String var2) {
      HashMap var3 = sFileTypeMap;
      MediaFile.MediaFileType var4 = new MediaFile.MediaFileType(var1, var2);
      var3.put(var0, var4);
      HashMap var6 = sMimeTypeMap;
      Integer var7 = new Integer(var1);
      var6.put(var2, var7);
   }

   public static MediaFile.MediaFileType getFileType(String var0) {
      int var1 = var0.lastIndexOf(".");
      MediaFile.MediaFileType var2;
      if(var1 < 0) {
         var2 = null;
      } else {
         HashMap var3 = sFileTypeMap;
         int var4 = var1 + 1;
         String var5 = var0.substring(var4).toUpperCase();
         var2 = (MediaFile.MediaFileType)var3.get(var5);
      }

      return var2;
   }

   public static int getFileTypeForMimeType(String var0) {
      Integer var1 = (Integer)sMimeTypeMap.get(var0);
      int var2;
      if(var1 == null) {
         var2 = 0;
      } else {
         var2 = var1.intValue();
      }

      return var2;
   }

   public static boolean isAudioFileType(int var0) {
      boolean var1;
      if((var0 < 1 || var0 > 8) && (var0 < 11 || var0 > 13)) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public static boolean isImageFileType(int var0) {
      boolean var1;
      if(var0 >= 31 && var0 <= 35) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static boolean isPlayListFileType(int var0) {
      boolean var1;
      if(var0 >= 41 && var0 <= 43) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static boolean isVideoFileType(int var0) {
      boolean var1;
      if(var0 >= 21 && var0 <= 25) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   static class MediaFileType {

      int fileType;
      String mimeType;


      MediaFileType(int var1, String var2) {
         this.fileType = var1;
         this.mimeType = var2;
      }
   }
}
