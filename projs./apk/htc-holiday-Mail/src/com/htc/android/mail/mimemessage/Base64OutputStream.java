package com.htc.android.mail.mimemessage;

import com.htc.android.mail.mimemessage.Base64;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Base64OutputStream extends FilterOutputStream {

   private final Base64 base64;
   private final boolean doEncode;
   private final byte[] singleByte;


   public Base64OutputStream(OutputStream var1) {
      this(var1, (boolean)1);
   }

   public Base64OutputStream(OutputStream var1, boolean var2) {
      super(var1);
      byte[] var3 = new byte[1];
      this.singleByte = var3;
      this.doEncode = var2;
      Base64 var4 = new Base64();
      this.base64 = var4;
   }

   public Base64OutputStream(OutputStream var1, boolean var2, int var3, byte[] var4) {
      super(var1);
      byte[] var5 = new byte[1];
      this.singleByte = var5;
      this.doEncode = var2;
      Base64 var6 = new Base64(var3, var4);
      this.base64 = var6;
   }

   private void flush(boolean var1) throws IOException {
      int var2 = this.base64.avail();
      if(var2 > 0) {
         byte[] var3 = new byte[var2];
         int var4 = this.base64.readResults(var3, 0, var2);
         if(var4 > 0) {
            this.out.write(var3, 0, var4);
         }
      }

      if(var1) {
         this.out.flush();
      }
   }

   public void close() throws IOException {
      if(this.doEncode) {
         Base64 var1 = this.base64;
         byte[] var2 = this.singleByte;
         var1.encode(var2, 0, -1);
      } else {
         Base64 var3 = this.base64;
         byte[] var4 = this.singleByte;
         var3.decode(var4, 0, -1);
      }

      this.flush();
   }

   public void flush() throws IOException {
      this.flush((boolean)1);
   }

   public void write(int var1) throws IOException {
      byte[] var2 = this.singleByte;
      byte var3 = (byte)var1;
      var2[0] = var3;
      byte[] var4 = this.singleByte;
      this.write(var4, 0, 1);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      if(var1 == null) {
         throw new NullPointerException();
      } else if(var2 >= 0 && var3 >= 0 && var2 + var3 >= 0) {
         int var4 = var1.length;
         if(var2 <= var4) {
            int var5 = var2 + var3;
            int var6 = var1.length;
            if(var5 <= var6) {
               if(var3 <= 0) {
                  return;
               }

               if(this.doEncode) {
                  this.base64.encode(var1, var2, var3);
               } else {
                  this.base64.decode(var1, var2, var3);
               }

               this.flush((boolean)0);
               return;
            }
         }

         throw new IndexOutOfBoundsException();
      } else {
         throw new IndexOutOfBoundsException();
      }
   }
}
