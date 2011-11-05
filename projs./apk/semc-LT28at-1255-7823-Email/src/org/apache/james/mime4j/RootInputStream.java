package org.apache.james.mime4j;

import java.io.IOException;
import java.io.InputStream;

class RootInputStream extends InputStream {

   private InputStream is = null;
   private int lineNumber = 1;
   private int prev = -1;
   private boolean truncated = 0;


   public RootInputStream(InputStream var1) {
      this.is = var1;
   }

   public int getLineNumber() {
      return this.lineNumber;
   }

   public int read() throws IOException {
      int var1;
      if(this.truncated) {
         var1 = -1;
      } else {
         int var2 = this.is.read();
         if(this.prev == 13 && var2 == 10) {
            int var3 = this.lineNumber + 1;
            this.lineNumber = var3;
         }

         this.prev = var2;
         var1 = var2;
      }

      return var1;
   }

   public int read(byte[] var1) throws IOException {
      int var2 = var1.length;
      return this.read(var1, 0, var2);
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      int var4;
      if(this.truncated) {
         var4 = -1;
      } else {
         int var5 = this.is.read(var1, var2, var3);
         int var6 = var2;

         while(true) {
            int var7 = var2 + var5;
            if(var6 >= var7) {
               var4 = var5;
               break;
            }

            if(this.prev == 13 && var1[var6] == 10) {
               int var8 = this.lineNumber + 1;
               this.lineNumber = var8;
            }

            byte var9 = var1[var6];
            this.prev = var9;
            ++var6;
         }
      }

      return var4;
   }

   public void truncate() {
      this.truncated = (boolean)1;
   }
}
