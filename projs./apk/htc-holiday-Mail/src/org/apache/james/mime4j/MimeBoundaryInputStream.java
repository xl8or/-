package org.apache.james.mime4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

public class MimeBoundaryInputStream extends InputStream {

   private byte[] boundary = null;
   private boolean eof = 0;
   private boolean first = 1;
   private boolean moreParts = 1;
   private boolean parenteof = 0;
   private PushbackInputStream s = null;


   public MimeBoundaryInputStream(InputStream var1, String var2) throws IOException {
      int var3 = var2.length() + 4;
      PushbackInputStream var4 = new PushbackInputStream(var1, var3);
      this.s = var4;
      String var5 = "--" + var2;
      byte[] var6 = new byte[var5.length()];
      this.boundary = var6;
      int var7 = 0;

      while(true) {
         int var8 = this.boundary.length;
         if(var7 >= var8) {
            int var11 = this.read();
            if(var11 == -1) {
               return;
            } else {
               this.s.unread(var11);
               return;
            }
         }

         byte[] var9 = this.boundary;
         byte var10 = (byte)var5.charAt(var7);
         var9[var7] = var10;
         ++var7;
      }
   }

   private boolean matchBoundary() throws IOException {
      int var1 = 0;

      boolean var8;
      while(true) {
         int var2 = this.boundary.length;
         if(var1 >= var2) {
            int var9 = this.s.read();
            int var10 = this.s.read();
            byte var11;
            if(var9 == 45 && var10 == 45) {
               var11 = 0;
            } else {
               var11 = 1;
            }

            this.moreParts = (boolean)var11;

            while(var10 != 10 || var9 != 13) {
               var9 = var10;
               var10 = this.s.read();
               if(var10 == -1) {
                  break;
               }
            }

            if(var10 == -1) {
               this.moreParts = (boolean)0;
               this.parenteof = (boolean)1;
            }

            this.eof = (boolean)1;
            var8 = true;
            break;
         }

         int var3 = this.s.read();
         byte var4 = this.boundary[var1];
         if(var3 != var4) {
            if(var3 != -1) {
               this.s.unread(var3);
            }

            for(int var5 = var1 - 1; var5 >= 0; var5 += -1) {
               PushbackInputStream var6 = this.s;
               byte var7 = this.boundary[var5];
               var6.unread(var7);
            }

            var8 = false;
            break;
         }

         ++var1;
      }

      return var8;
   }

   public void close() throws IOException {
      this.s.close();
   }

   public void consume() throws IOException {
      while(this.read() != -1) {
         ;
      }

   }

   public boolean hasMoreParts() {
      return this.moreParts;
   }

   public boolean parentEOF() {
      return this.parenteof;
   }

   public int read() throws IOException {
      int var1;
      if(this.eof) {
         var1 = -1;
      } else {
         if(this.first) {
            this.first = (boolean)0;
            if(this.matchBoundary()) {
               var1 = -1;
               return var1;
            }
         }

         int var2 = this.s.read();
         int var3 = this.s.read();
         if(var2 == 13 && var3 == 10 && this.matchBoundary()) {
            var1 = -1;
         } else {
            if(var3 != -1) {
               this.s.unread(var3);
            }

            byte var4;
            if(var2 == -1) {
               var4 = 1;
            } else {
               var4 = 0;
            }

            this.parenteof = (boolean)var4;
            boolean var5 = this.parenteof;
            this.eof = var5;
            var1 = var2;
         }
      }

      return var1;
   }
}
