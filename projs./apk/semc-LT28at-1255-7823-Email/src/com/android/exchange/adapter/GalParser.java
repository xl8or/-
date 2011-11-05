package com.android.exchange.adapter;

import com.android.exchange.EasSyncService;
import com.android.exchange.adapter.Parser;
import com.android.exchange.provider.GalResult;
import java.io.IOException;
import java.io.InputStream;

public class GalParser extends Parser {

   GalResult mGalResult;
   private EasSyncService mService;


   public GalParser(InputStream var1, EasSyncService var2) throws IOException {
      super(var1);
      GalResult var3 = new GalResult();
      this.mGalResult = var3;
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
               this.parseResponse(var1);
            } else {
               this.skipTag();
            }
         }

         boolean var2;
         if(this.mGalResult.total > 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }
   }

   public void parseProperties(GalResult var1) throws IOException {
      String var2 = null;
      String var3 = null;

      while(this.nextTag(967) != 3) {
         if(this.tag == 1029) {
            var2 = this.getValue();
         } else if(this.tag == 1039) {
            var3 = this.getValue();
         } else {
            this.skipTag();
         }
      }

      if(var2 != null) {
         if(var3 != null) {
            var1.addGalData(0L, var2, var3);
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
         } else if(this.tag == 976) {
            int var3 = this.getValueInt();
            var1.total = var3;
         } else {
            this.skipTag();
         }
      }

   }
}
