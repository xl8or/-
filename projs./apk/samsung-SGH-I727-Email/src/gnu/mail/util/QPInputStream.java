package gnu.mail.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

public class QPInputStream extends FilterInputStream {

   private static final int CR = 13;
   private static final int EQ = 61;
   private static final int LF = 10;
   private static final int SPACE = 32;
   protected byte[] buf;
   protected int spaceCount;


   public QPInputStream(InputStream var1) {
      PushbackInputStream var2 = new PushbackInputStream(var1, 2);
      super(var2);
      byte[] var3 = new byte[2];
      this.buf = var3;
   }

   public int available() throws IOException {
      return this.in.available();
   }

   public boolean markSupported() {
      return false;
   }

   public int read() throws IOException {
      int var2;
      if(this.spaceCount > 0) {
         int var1 = this.spaceCount - 1;
         this.spaceCount = var1;
         var2 = 32;
      } else {
         int var3 = this.in.read();
         if(var3 == 32) {
            while(true) {
               var2 = this.in.read();
               if(var2 != 32) {
                  if(var2 != 10 && var2 != 13 && var2 != -1) {
                     ((PushbackInputStream)this.in).unread(var2);
                     var2 = 32;
                  } else {
                     this.spaceCount = 0;
                  }
                  break;
               }

               int var4 = this.spaceCount + 1;
               this.spaceCount = var4;
            }
         } else if(var3 == 61) {
            var2 = super.in.read();
            if(var2 == 10) {
               var2 = this.read();
            } else if(var2 == 13) {
               var3 = this.in.read();
               if(var3 != 10) {
                  ((PushbackInputStream)this.in).unread(var3);
               }

               var2 = this.read();
            } else if(var2 != -1) {
               byte[] var5 = this.buf;
               byte var6 = (byte)var2;
               var5[0] = var6;
               byte[] var7 = this.buf;
               byte var8 = (byte)this.in.read();
               var7[1] = var8;

               int var10;
               try {
                  byte[] var9 = this.buf;
                  var10 = Integer.parseInt(new String(var9, 0, 2), 16);
               } catch (NumberFormatException var14) {
                  PushbackInputStream var12 = (PushbackInputStream)this.in;
                  byte[] var13 = this.buf;
                  var12.unread(var13);
                  var2 = var3;
                  return var2;
               }

               var2 = var10;
            }
         } else {
            var2 = var3;
         }
      }

      return var2;
   }

   public int read(byte[] param1, int param2, int param3) throws IOException {
      // $FF: Couldn't be decompiled
   }
}
