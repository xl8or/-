package gnu.mail.providers.mbox;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

class MboxOutputStream extends FilterOutputStream {

   private static byte KET = 62;
   protected byte[] buf;
   protected int count;


   public MboxOutputStream(OutputStream var1) {
      this(var1, 4096);
   }

   public MboxOutputStream(OutputStream var1, int var2) {
      super(var1);
      this.count = 0;
      byte[] var3 = new byte[var2];
      this.buf = var3;
   }

   public void flush() throws IOException {
      synchronized(this){}

      try {
         this.validateAndFlushBuffer();
         this.out.flush();
      } finally {
         ;
      }

   }

   protected void validateAndFlushBuffer() throws IOException {
      if(this.count > 0) {
         int var1 = 0;
         boolean var2 = false;

         while(true) {
            int var3 = this.count - 5;
            if(var1 >= var3 || var2) {
               OutputStream var22 = this.out;
               byte[] var23 = this.buf;
               int var24 = this.count;
               var22.write(var23, 0, var24);
               this.count = 0;
               return;
            }

            label34: {
               if(this.buf[var1] == 70) {
                  byte[] var4 = this.buf;
                  int var5 = var1 + 1;
                  if(var4[var5] == 114) {
                     byte[] var6 = this.buf;
                     int var7 = var1 + 2;
                     if(var6[var7] == 111) {
                        byte[] var8 = this.buf;
                        int var9 = var1 + 3;
                        if(var8[var9] == 109) {
                           byte[] var10 = this.buf;
                           int var11 = var1 + 4;
                           if(var10[var11] == 32) {
                              byte[] var12 = new byte[this.buf.length + 1];
                              byte[] var13 = this.buf;
                              int var14 = this.buf.length;
                              System.arraycopy(var13, 0, var12, 0, var14);
                              byte var15 = KET;
                              var12[var1] = var15;
                              byte[] var16 = this.buf;
                              int var17 = var1 + 1;
                              int var18 = this.buf.length - var1;
                              System.arraycopy(var16, var1, var12, var17, var18);
                              this.buf = var12;
                              int var19 = this.count + 1;
                              this.count = var19;
                              var2 = true;
                              break label34;
                           }
                        }
                     }
                  }
               }

               byte var20 = this.buf[var1];
               byte var21 = KET;
               if(var20 != var21 && this.buf[var1] != 10) {
                  var2 = true;
               }
            }

            ++var1;
         }
      }
   }

   public void write(int var1) throws IOException {
      synchronized(this){}
      if(var1 != 13) {
         try {
            int var2 = this.count;
            int var3 = this.buf.length;
            if(var2 > var3) {
               this.validateAndFlushBuffer();
            }

            byte[] var4 = this.buf;
            int var5 = this.count;
            int var6 = var5 + 1;
            this.count = var6;
            byte var7 = (byte)var1;
            var4[var5] = var7;
            if(var1 == 10) {
               this.validateAndFlushBuffer();
            }
         } finally {
            ;
         }
      }

   }

   public void write(byte[] param1, int param2, int param3) throws IOException {
      // $FF: Couldn't be decompiled
   }
}
