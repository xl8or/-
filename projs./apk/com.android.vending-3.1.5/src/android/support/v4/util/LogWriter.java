package android.support.v4.util;

import android.util.Log;
import java.io.Writer;

public class LogWriter extends Writer {

   private StringBuilder mBuilder;
   private final String mTag;


   public LogWriter(String var1) {
      StringBuilder var2 = new StringBuilder(128);
      this.mBuilder = var2;
      this.mTag = var1;
   }

   private void flushBuilder() {
      if(this.mBuilder.length() > 0) {
         String var1 = this.mTag;
         String var2 = this.mBuilder.toString();
         Log.d(var1, var2);
         StringBuilder var4 = this.mBuilder;
         int var5 = this.mBuilder.length();
         var4.delete(0, var5);
      }
   }

   public void close() {
      this.flushBuilder();
   }

   public void flush() {
      this.flushBuilder();
   }

   public void write(char[] var1, int var2, int var3) {
      for(int var4 = 0; var4 < var3; ++var4) {
         int var5 = var2 + var4;
         char var6 = var1[var5];
         if(var6 == 10) {
            this.flushBuilder();
         } else {
            this.mBuilder.append(var6);
         }
      }

   }
}
