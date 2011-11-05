package com.htc.android.mail;

import com.htc.android.mail.Mail;
import com.htc.android.mail.ll;
import java.io.IOException;
import java.io.OutputStream;

public class CountSizeOutputStream extends OutputStream {

   private int mSize = 0;


   public CountSizeOutputStream() {}

   public int getSize() {
      return this.mSize;
   }

   public void setSize(int var1) {
      this.mSize = var1;
   }

   public void write(int var1) throws IOException {
      int var2 = this.mSize + 1;
      this.mSize = var2;
      if(Mail.MAIL_DEBUG) {
         StringBuilder var3 = (new StringBuilder()).append("mSize:");
         int var4 = this.mSize;
         String var5 = var3.append(var4).toString();
         ll.i("CountSizeOutputStream", var5);
      }
   }

   public void write(String var1) throws IOException {
      int var2 = this.mSize;
      int var3 = var1.length();
      int var4 = var2 + var3;
      this.mSize = var4;
      if(Mail.MAIL_DEBUG) {
         StringBuilder var5 = (new StringBuilder()).append("mSize:");
         int var6 = this.mSize;
         String var7 = var5.append(var6).toString();
         ll.i("CountSizeOutputStream", var7);
      }
   }

   public void write(byte[] var1) throws IOException {
      int var2 = this.mSize;
      int var3 = var1.length;
      int var4 = var2 + var3;
      this.mSize = var4;
      if(Mail.MAIL_DEBUG) {
         StringBuilder var5 = (new StringBuilder()).append("mSize:");
         int var6 = this.mSize;
         String var7 = var5.append(var6).toString();
         ll.i("CountSizeOutputStream", var7);
      }
   }
}
