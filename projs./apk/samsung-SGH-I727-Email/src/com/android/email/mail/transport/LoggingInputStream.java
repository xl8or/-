package com.android.email.mail.transport;

import android.util.Log;
import com.android.email.Utility;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LoggingInputStream extends FilterInputStream {

   private boolean mDumpEmptyLines;
   private StringBuilder mSb;
   private final String mTag;


   public LoggingInputStream(InputStream var1) {
      this(var1, "RAW", (boolean)0);
   }

   public LoggingInputStream(InputStream var1, String var2, boolean var3) {
      super(var1);
      String var4 = var2 + " ";
      this.mTag = var4;
      this.mDumpEmptyLines = var3;
      this.initBuffer();
      StringBuilder var5 = new StringBuilder();
      String var6 = this.mTag;
      String var7 = var5.append(var6).append("dump start").toString();
      int var8 = Log.d("Email", var7);
   }

   private void flushLog() {
      if(!this.mDumpEmptyLines) {
         int var1 = this.mSb.length();
         int var2 = this.mTag.length();
         if(var1 <= var2) {
            return;
         }
      }

      String var3 = this.mSb.toString();
      int var4 = Log.d("Email", var3);
      this.initBuffer();
   }

   private void initBuffer() {
      String var1 = this.mTag;
      StringBuilder var2 = new StringBuilder(var1);
      this.mSb = var2;
   }

   private void logRaw(int var1) {
      if(var1 != 13) {
         if(var1 == 10) {
            this.flushLog();
         } else if(32 <= var1 && var1 <= 126) {
            StringBuilder var2 = this.mSb;
            char var3 = (char)var1;
            var2.append(var3);
         } else {
            StringBuilder var5 = this.mSb;
            StringBuilder var6 = (new StringBuilder()).append("\\x");
            String var7 = Utility.byteToHex(var1);
            String var8 = var6.append(var7).toString();
            var5.append(var8);
         }
      }
   }

   public void close() throws IOException {
      super.close();
      this.flushLog();
   }

   public int read() throws IOException {
      int var1 = super.read();
      this.logRaw(var1);
      return var1;
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      int var4 = super.read(var1, var2, var3);

      for(int var5 = var4; var5 > 0; ++var2) {
         int var6 = var1[var2] & 255;
         this.logRaw(var6);
         var5 += -1;
      }

      return var4;
   }
}
