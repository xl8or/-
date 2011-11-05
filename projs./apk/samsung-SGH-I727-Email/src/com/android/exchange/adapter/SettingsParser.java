package com.android.exchange.adapter;

import android.util.Log;
import com.android.exchange.EasException;
import com.android.exchange.adapter.Parser;
import java.io.IOException;
import java.io.InputStream;

public class SettingsParser extends Parser {

   private static final String TAG = SettingsParser.class.getSimpleName();
   private int mStatus = 1;


   public SettingsParser(InputStream var1) throws IOException {
      super(var1);
   }

   private boolean parseSetTag() throws IOException {
      boolean var1 = true;

      while(this.nextTag(1160) != 3) {
         if(this.tag == 1158) {
            int var2 = this.getValueInt();
            this.mStatus = var2;
            if(this.mStatus != 1) {
               var1 = false;
            }
         } else {
            this.skipTag();
         }
      }

      return var1;
   }

   private boolean parseSettings() throws IOException {
      boolean var1 = true;

      while(this.nextTag(1157) != 3) {
         if(this.tag == 1158) {
            int var2 = this.getValueInt();
            this.mStatus = var2;
            if(this.mStatus != 1) {
               var1 = false;
            }
         } else if(this.tag == 1174) {
            var1 = this.parserDeviceInformation();
         } else if(this.tag == 1172) {
            var1 = this.parserDevicePassword();
         } else {
            this.skipTag();
         }
      }

      return var1;
   }

   private boolean parserDeviceInformation() throws IOException {
      boolean var1 = true;

      while(this.nextTag(1174) != 3) {
         if(this.tag == 1160) {
            var1 = this.parseSetTag();
         } else {
            this.skipTag();
         }
      }

      return var1;
   }

   private boolean parserDevicePassword() throws IOException {
      boolean var1 = true;

      while(this.nextTag(1172) != 3) {
         if(this.tag == 1160) {
            var1 = this.parseSetTag();
         } else {
            this.skipTag();
         }
      }

      return var1;
   }

   public int getStatus() {
      return this.mStatus;
   }

   public boolean parse() throws IOException, EasException {
      int var1 = Log.d(TAG, "parsing the document");
      if(this.nextTag(0) != 1157) {
         throw new IOException();
      } else {
         boolean var2 = this.parseSettings();
         int var3 = Log.d(TAG, "document parsing done");
         return var2;
      }
   }
}
