package com.android.exchange.adapter;

import android.util.Base64;
import com.android.exchange.EasSyncService;
import com.android.exchange.adapter.Parser;
import com.android.exchange.provider.GalResult;
import java.io.IOException;
import java.io.InputStream;

public class GalParser extends Parser {

   public static final int GAL_PHOTO_MAXPICTURES_EXCEEDED = 175;
   public static final int GAL_PHOTO_MAXSIZE_EXCEEDED = 174;
   public static final int GAL_PHOTO_NOT_AVAILABLE = 173;
   public static final int GAL_PHOTO_SUCCESS = 1;
   String mGalPictureData;
   GalResult mGalResult;
   private EasSyncService mService;


   public GalParser(InputStream var1, EasSyncService var2) throws IOException {
      super(var1);
      GalResult var3 = new GalResult();
      this.mGalResult = var3;
      this.mGalPictureData = null;
      this.mService = var2;
   }

   public GalResult getGalResult() {
      return this.mGalResult;
   }

   public boolean parse() throws IOException {
      if(this.nextTag(0) != 965) {
         throw new IOException();
      } else {
         while(this.nextTag(0) != 3) {
            if(this.tag == 973) {
               GalResult var1 = this.mGalResult;
               double var2 = this.mService.mProtocolVersionDouble.doubleValue();
               var1.protocolVerison = var2;
               GalResult var4 = this.mGalResult;
               this.parseResponse(var4);
            } else {
               this.skipTag();
            }
         }

         boolean var5;
         if(this.mGalResult.total > 0) {
            var5 = true;
         } else {
            var5 = false;
         }

         return var5;
      }
   }

   public void parsePicture() throws IOException {
      this.mGalPictureData = null;

      while(this.nextTag(1040) != 3) {
         if(this.tag == 1041) {
            int var1 = this.getValueInt();
         } else if(this.tag == 1042) {
            String var2 = Base64.encodeToString(this.getValueOpaque(), 0);
            this.mGalPictureData = var2;
         } else {
            this.skipTag();
         }
      }

   }

   public void parseProperties(GalResult var1) throws IOException {
      String var2 = null;
      String var3 = null;
      String var4 = null;
      String var5 = null;
      String var6 = null;
      String var7 = null;
      String var8 = null;
      String var9 = null;
      String var10 = null;
      String var11 = null;
      String var12 = null;

      while(this.nextTag(967) != 3) {
         if(this.tag == 1029) {
            var2 = this.getValue();
         } else if(this.tag == 1030) {
            var3 = this.getValue();
         } else if(this.tag == 1031) {
            var4 = this.getValue();
         } else if(this.tag == 1032) {
            var5 = this.getValue();
         } else if(this.tag == 1033) {
            var6 = this.getValue();
         } else if(this.tag == 1034) {
            var7 = this.getValue();
         } else if(this.tag == 1039) {
            var8 = this.getValue();
         } else if(this.tag == 1035) {
            var9 = this.getValue();
         } else if(this.tag == 1036) {
            var10 = this.getValue();
         } else if(this.tag == 1037) {
            var11 = this.getValue();
         } else if(this.tag == 1038) {
            var12 = this.getValue();
         } else if(this.tag == 1040) {
            this.parsePicture();
         } else {
            this.skipTag();
         }
      }

      if(var2 != null) {
         if(var8 != null) {
            String var13 = this.mGalPictureData;
            var1.addGalData(0L, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
         }
      }
   }

   public void parseResponse(GalResult var1) throws IOException {
      while(this.nextTag(973) != 3) {
         if(this.tag == 967) {
            this.parseStore(var1);
         } else {
            this.skipTag();
         }
      }

   }

   public void parseResult(GalResult var1) throws IOException {
      while(this.nextTag(967) != 3) {
         if(this.tag == 975) {
            this.parseProperties(var1);
         } else {
            this.skipTag();
         }
      }

   }

   public void parseStore(GalResult var1) throws IOException {
      while(this.nextTag(967) != 3) {
         if(this.tag == 974) {
            this.parseResult(var1);
         } else if(this.tag == 971) {
            String var2 = this.getValue();
            if(var2 != null) {
               String[] var3 = var2.split("-");
               if(var3.length == 2) {
                  int var4 = Integer.parseInt(var3[0]);
                  var1.startRange = var4;
                  int var5 = Integer.parseInt(var3[1]);
                  var1.endRange = var5;
               }
            }
         } else if(this.tag == 976) {
            int var6 = this.getValueInt();
            var1.total = var6;
         } else {
            this.skipTag();
         }
      }

   }
}
