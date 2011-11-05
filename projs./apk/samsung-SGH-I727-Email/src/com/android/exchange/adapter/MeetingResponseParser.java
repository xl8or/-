package com.android.exchange.adapter;

import com.android.exchange.EasSyncService;
import com.android.exchange.adapter.Parser;
import java.io.IOException;
import java.io.InputStream;

public class MeetingResponseParser extends Parser {

   private EasSyncService mService;


   public MeetingResponseParser(InputStream var1, EasSyncService var2) throws IOException {
      super(var1);
      this.mService = var2;
   }

   public boolean parse() throws IOException {
      if(this.nextTag(0) != 519) {
         throw new IOException();
      } else {
         while(this.nextTag(0) != 3) {
            if(this.tag == 522) {
               this.parseResult();
            } else {
               this.skipTag();
            }
         }

         return false;
      }
   }

   public void parseResult() throws IOException {
      while(this.nextTag(522) != 3) {
         if(this.tag == 523) {
            int var1 = this.getValueInt();
            if(var1 != 1) {
               EasSyncService var2 = this.mService;
               String[] var3 = new String[1];
               String var4 = "Error in meeting response: " + var1;
               var3[0] = var4;
               var2.userLog(var3);
            }
         } else if(this.tag == 517) {
            EasSyncService var5 = this.mService;
            String[] var6 = new String[1];
            StringBuilder var7 = (new StringBuilder()).append("Meeting response calendar id: ");
            String var8 = this.getValue();
            String var9 = var7.append(var8).toString();
            var6[0] = var9;
            var5.userLog(var6);
         } else {
            this.skipTag();
         }
      }

   }
}
