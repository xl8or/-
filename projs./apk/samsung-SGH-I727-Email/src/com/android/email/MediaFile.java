package com.android.email;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

public class MediaFile {

   public static final int FILE_ICON_DEFAULT_LARGE = 0;
   public static final int FILE_ICON_DEFAULT_SMALL = 0;
   public static final int FILE_TYPE_3GA = 9;
   public static final int FILE_TYPE_3GPP = 23;
   public static final int FILE_TYPE_3GPP2 = 24;
   public static final int FILE_TYPE_AAC = 8;
   public static final int FILE_TYPE_AMR = 4;
   public static final int FILE_TYPE_APK = 100;
   public static final int FILE_TYPE_ASF = 27;
   public static final int FILE_TYPE_AVI = 28;
   public static final int FILE_TYPE_AWB = 5;
   public static final int FILE_TYPE_BMP = 54;
   public static final int FILE_TYPE_DCF = 87;
   public static final int FILE_TYPE_DIVX = 29;
   public static final int FILE_TYPE_DOC = 82;
   public static final int FILE_TYPE_FLV = 30;
   public static final int FILE_TYPE_GIF = 52;
   public static final int FILE_TYPE_IMY = 13;
   public static final int FILE_TYPE_JAD = 110;
   public static final int FILE_TYPE_JAR = 111;
   public static final int FILE_TYPE_JPEG = 51;
   public static final int FILE_TYPE_M3U = 71;
   public static final int FILE_TYPE_M4A = 2;
   public static final int FILE_TYPE_M4V = 22;
   public static final int FILE_TYPE_MID = 11;
   public static final int FILE_TYPE_MKV = 31;
   public static final int FILE_TYPE_MP3 = 1;
   public static final int FILE_TYPE_MP4 = 21;
   public static final int FILE_TYPE_MPG = 26;
   public static final int FILE_TYPE_ODF = 88;
   public static final int FILE_TYPE_OGG = 7;
   public static final int FILE_TYPE_PDF = 81;
   public static final int FILE_TYPE_PLS = 72;
   public static final int FILE_TYPE_PNG = 53;
   public static final int FILE_TYPE_PPT = 84;
   public static final int FILE_TYPE_QSS = 86;
   public static final int FILE_TYPE_SDP = 32;
   public static final int FILE_TYPE_SMF = 12;
   public static final int FILE_TYPE_SVG = 91;
   public static final int FILE_TYPE_SWF = 90;
   public static final int FILE_TYPE_TXT = 85;
   public static final int FILE_TYPE_VCF = 121;
   public static final int FILE_TYPE_VCS = 120;
   public static final int FILE_TYPE_VNT = 122;
   public static final int FILE_TYPE_VTS = 123;
   public static final int FILE_TYPE_WAV = 3;
   public static final int FILE_TYPE_WBMP = 55;
   public static final int FILE_TYPE_WMA = 6;
   public static final int FILE_TYPE_WMV = 25;
   public static final int FILE_TYPE_WPL = 73;
   public static final int FILE_TYPE_XLS = 83;
   private static final int FIRST_AUDIO_FILE_TYPE = 1;
   private static final int FIRST_DOCUMENT_FILE_TYPE = 81;
   private static final int FIRST_DRM_FILE_TYPE = 87;
   private static final int FIRST_FLASH_FILE_TYPE = 90;
   private static final int FIRST_IMAGE_FILE_TYPE = 51;
   private static final int FIRST_INSTALL_FILE_TYPE = 100;
   private static final int FIRST_JAVA_FILE_TYPE = 110;
   private static final int FIRST_MIDI_FILE_TYPE = 11;
   private static final int FIRST_PLAYLIST_FILE_TYPE = 71;
   private static final int FIRST_VIDEO_FILE_TYPE = 21;
   private static final int LAST_AUDIO_FILE_TYPE = 8;
   private static final int LAST_DOCUMENT_FILE_TYPE = 85;
   private static final int LAST_DRM_FILE_TYPE = 88;
   private static final int LAST_FLASH_FILE_TYPE = 91;
   private static final int LAST_IMAGE_FILE_TYPE = 55;
   private static final int LAST_INSTALL_FILE_TYPE = 100;
   private static final int LAST_JAVA_FILE_TYPE = 111;
   private static final int LAST_MIDI_FILE_TYPE = 13;
   private static final int LAST_PLAYLIST_FILE_TYPE = 73;
   private static final int LAST_VIDEO_FILE_TYPE = 29;
   public static final String UNKNOWN_STRING = "<unknown>";
   public static String sFileExtensions;
   private static HashMap<String, MediaFile.MediaFileType> sFileTypeMap = new HashMap();
   private static HashMap<String, String> sMimeType = new HashMap();
   private static HashMap<String, Integer> sMimeTypeMap = new HashMap();


   static {
      byte var0 = 0;
      addFileType("MP3", 1, "audio/mpeg", "Mpeg", 0, var0);
      byte var1 = 0;
      addFileType("M4A", 2, "audio/mp4", "M4A", 0, var1);
      byte var2 = 0;
      addFileType("WAV", 3, "audio/x-wav", "WAVE", 0, var2);
      byte var3 = 0;
      addFileType("AMR", 4, "audio/amr", "AMR", 0, var3);
      byte var4 = 0;
      addFileType("AWB", 5, "audio/amr-wb", "AWB", 0, var4);
      byte var5 = 0;
      addFileType("WMA", 6, "audio/x-ms-wma", "WMA", 0, var5);
      byte var6 = 0;
      addFileType("OGG", 7, "audio/ogg", "OGG", 0, var6);
      byte var7 = 0;
      addFileType("AAC", 8, "audio/aac", "AAC", 0, var7);
      byte var8 = 11;
      String var9 = "audio/midi";
      byte var10 = 0;
      addFileType("MID", var8, var9, "MIDI", 0, var10);
      byte var11 = 11;
      String var12 = "audio/midi";
      byte var13 = 0;
      addFileType("XMF", var11, var12, "XMF", 0, var13);
      byte var14 = 11;
      String var15 = "audio/midi";
      byte var16 = 0;
      addFileType("MXMF", var14, var15, "MXMF", 0, var16);
      byte var17 = 11;
      String var18 = "audio/midi";
      byte var19 = 0;
      addFileType("RTTTL", var17, var18, "RTTTL", 0, var19);
      byte var20 = 0;
      addFileType("SMF", 12, "audio/sp-midi", "SMF", 0, var20);
      byte var21 = 0;
      addFileType("IMY", 13, "audio/imelody", "IMY", 0, var21);
      byte var22 = 11;
      String var23 = "audio/midi";
      byte var24 = 0;
      addFileType("MIDI", var22, var23, "MIDI", 0, var24);
      byte var25 = 0;
      addFileType("3GA", 9, "audio/3gpp", "3GA", 0, var25);
      byte var26 = 0;
      addFileType("MPEG", 26, "video/mpeg", "MPEG", 0, var26);
      byte var27 = 0;
      addFileType("MPG", 26, "video/mpeg", "MPEG", 0, var27);
      byte var28 = 0;
      addFileType("MP4", 21, "video/mp4", "MP4", 0, var28);
      byte var29 = 0;
      addFileType("M4V", 22, "video/mp4", "M4V", 0, var29);
      byte var30 = 0;
      addFileType("3GP", 23, "video/3gpp", "3GP", 0, var30);
      byte var31 = 0;
      addFileType("3GPP", 23, "video/3gpp", "3GPP", 0, var31);
      byte var32 = 0;
      addFileType("3G2", 24, "video/3gpp2", "3G2", 0, var32);
      byte var33 = 0;
      addFileType("3GPP2", 24, "video/3gpp2", "3GPP2", 0, var33);
      byte var34 = 0;
      addFileType("WMV", 25, "video/x-ms-wmv", "WMV", 0, var34);
      byte var35 = 0;
      addFileType("ASF", 27, "video/x-ms-asf", "ASF", 0, var35);
      byte var36 = 0;
      addFileType("AVI", 28, "video/avi", "AVI", 0, var36);
      byte var37 = 0;
      addFileType("DIVX", 29, "video/divx", "DIVX", 0, var37);
      byte var38 = 0;
      addFileType("FLV", 30, "video/flv", "FLV", 0, var38);
      byte var39 = 0;
      addFileType("MKV", 31, "video/mkv", "MKV", 0, var39);
      byte var40 = 0;
      addFileType("SDP", 32, "application/sdp", "SDP", 0, var40);
      byte var41 = 51;
      String var42 = "JPEG";
      byte var43 = 0;
      addFileType("JPG", var41, "image/jpeg", var42, 0, var43);
      String var44 = "JPEG";
      byte var45 = 51;
      String var46 = "JPEG";
      byte var47 = 0;
      addFileType(var44, var45, "image/jpeg", var46, 0, var47);
      byte var48 = 51;
      String var49 = "JPEG";
      byte var50 = 0;
      addFileType("MY5", var48, "image/vnd.tmo.my5", var49, 0, var50);
      byte var51 = 0;
      addFileType("GIF", 52, "image/gif", "GIF", 0, var51);
      byte var52 = 0;
      addFileType("PNG", 53, "image/png", "PNG", 0, var52);
      byte var53 = 0;
      addFileType("BMP", 54, "image/x-ms-bmp", "Microsoft BMP", 0, var53);
      byte var54 = 0;
      addFileType("WBMP", 55, "image/vnd.wap.wbmp", "Wireless BMP", 0, var54);
      byte var55 = 0;
      addFileType("QSS", 86, "slide/qss", "QSS", 0, var55);
      byte var56 = 0;
      addFileType("M3U", 71, "audio/x-mpegurl", "M3U", 0, var56);
      byte var57 = 0;
      addFileType("PLS", 72, "audio/x-scpls", "WPL", 0, var57);
      byte var58 = 0;
      addFileType("WPL", 73, "application/vnd.ms-wpl", " ", 0, var58);
      byte var59 = 0;
      addFileType("PDF", 81, "application/pdf", "Acrobat PDF", 0, var59);
      byte var60 = 0;
      addFileType("DOC", 82, "application/msword", "Microsoft Office WORD", 0, var60);
      byte var61 = 0;
      addFileType("DOCX", 82, "application/msword", "Microsoft Office WORD", 0, var61);
      byte var62 = 0;
      addFileType("XLS", 83, "application/vnd.ms-excel", "Microsoft Office Excel", 0, var62);
      byte var63 = 0;
      addFileType("XLSX", 83, "application/vnd.ms-excel", "Microsoft Office Excel", 0, var63);
      byte var64 = 0;
      addFileType("PPT", 84, "application/vnd.ms-powerpoint", "Microsoft Office PowerPoint", 0, var64);
      byte var65 = 0;
      addFileType("PPTX", 84, "application/vnd.ms-powerpoint", "Microsoft Office PowerPoint", 0, var65);
      byte var66 = 0;
      addFileType("TXT", 85, "text/plain", "Text Document", 0, var66);
      byte var67 = 0;
      addFileType("SWF", 90, "application/x-shockwave-flash", "SWF", 0, var67);
      byte var68 = 0;
      addFileType("SVG", 91, "image/svg+xml", "SVG", 0, var68);
      byte var69 = 0;
      addFileType("DCF", 87, "application/vnd.oma.drm.content", "DRM Content", 0, var69);
      byte var70 = 0;
      addFileType("ODF", 88, "application/vnd.oma.drm.content", "DRM Content", 0, var70);
      byte var71 = 0;
      addFileType("APK", 100, "application/apk", "Android package install file", 0, var71);
      byte var72 = 0;
      addFileType("JAD", 110, "text/vnd.sun.j2me.app-descriptor ", "JAD", 0, var72);
      byte var73 = 0;
      addFileType("JAR", 111, "application/java-archive ", "JAR", 0, var73);
      byte var74 = 0;
      addFileType("VCS", 120, "text/x-vCalendar", "VCS", 0, var74);
      byte var75 = 0;
      addFileType("VCF", 121, "text/x-vcard", "VCF", 0, var75);
      byte var76 = 0;
      addFileType("VNT", 122, "text/x-vnote", "VNT", 0, var76);
      byte var77 = 0;
      addFileType("VTS", 122, "text/x-vtodo", "VTS", 0, var77);
      StringBuilder var78 = new StringBuilder();
      Iterator var79 = sFileTypeMap.keySet().iterator();

      while(var79.hasNext()) {
         if(var78.length() > 0) {
            StringBuilder var80 = var78.append(',');
         }

         String var81 = (String)var79.next();
         var78.append(var81);
      }

      sFileExtensions = var78.toString();
   }

   public MediaFile() {}

   static void addFileType(String var0, int var1, String var2, String var3, int var4, int var5) {
      HashMap var6 = sFileTypeMap;
      MediaFile.MediaFileType var12 = new MediaFile.MediaFileType(var1, var2, var3, var4, var5);
      var6.put(var0, var12);
      HashMap var14 = sMimeTypeMap;
      Integer var15 = Integer.valueOf(var1);
      var14.put(var2, var15);
      sMimeType.put(var0, var2);
   }

   public static String getDescription(String var0) {
      MediaFile.MediaFileType var1 = getFileType(var0);
      String var2;
      if(var1 == null) {
         var2 = "";
      } else {
         var2 = var1.description;
      }

      return var2;
   }

   public static MediaFile.MediaFileType getFileType(String var0) {
      int var1 = var0.lastIndexOf(46);
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

   public static int getFileTypeInt(String var0) {
      MediaFile.MediaFileType var1 = getFileType(var0);
      int var2;
      if(var1 == null) {
         var2 = 0;
      } else {
         var2 = var1.fileType;
      }

      return var2;
   }

   public static int getLargeIcon(String var0) {
      MediaFile.MediaFileType var1 = getFileType(var0);
      int var2;
      if(var1 == null) {
         var2 = 0;
      } else {
         var2 = var1.iconLarge;
      }

      return var2;
   }

   public static Drawable getLargeIconDrawable(File var0, Activity var1) {
      Resources var2 = var1.getResources();
      int var3 = getLargeIcon(var0.getName());
      return var2.getDrawable(var3);
   }

   public static String getMimeType(String var0) {
      MediaFile.MediaFileType var1 = getFileType(var0);
      String var2;
      if(var1 == null) {
         var2 = "";
      } else {
         var2 = var1.mimeType;
      }

      return var2;
   }

   public static String getMimeTypeForExtention(String var0) {
      return (String)sMimeType.get(var0);
   }

   public static String getShareMimeType(String var0) {
      MediaFile.MediaFileType var1 = getFileType(var0);
      String var2;
      if(var1 == null) {
         var2 = "application/*";
      } else {
         var2 = var1.mimeType;
      }

      return var2;
   }

   public static int getSmallIcon(String var0) {
      MediaFile.MediaFileType var1 = getFileType(var0);
      int var2;
      if(var1 == null) {
         var2 = 0;
      } else {
         var2 = var1.iconSmall;
      }

      return var2;
   }

   public static Drawable getSmallIconDrawable(File var0, Activity var1) {
      Resources var2 = var1.getResources();
      int var3 = getSmallIcon(var0.getName());
      return var2.getDrawable(var3);
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

   public static boolean isDocumentFileType(int var0) {
      boolean var1;
      if(var0 >= 81 && var0 <= 85) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static boolean isDrmFileType(int var0) {
      boolean var1;
      if(var0 >= 87 && var0 <= 88) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static boolean isFlashFileType(int var0) {
      boolean var1;
      if(var0 >= 90 && var0 <= 91) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static boolean isImageFileType(int var0) {
      boolean var1;
      if(var0 >= 51 && var0 <= 55) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static boolean isInstallFileType(int var0) {
      boolean var1;
      if(var0 >= 100 && var0 <= 100) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static boolean isJavaFileType(int var0) {
      boolean var1;
      if(var0 >= 110 && var0 <= 111) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static boolean isPlayListFileType(int var0) {
      boolean var1;
      if(var0 >= 71 && var0 <= 73) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static boolean isVideoFileType(int var0) {
      boolean var1;
      if(var0 >= 21 && var0 <= 29) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   static class MediaFileType {

      String description;
      int fileType;
      int iconLarge;
      int iconSmall;
      String mimeType;


      MediaFileType(int var1, String var2, String var3, int var4, int var5) {
         this.fileType = var1;
         this.mimeType = var2;
         this.description = var3;
         this.iconSmall = var4;
         this.iconLarge = var5;
      }
   }
}
