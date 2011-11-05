package com.android.email.mail.transport;

import android.util.Log;
import java.util.ArrayList;

public class DiscourseLogger {

   private String[] mBuffer;
   private final int mBufferSize;
   private int mPos;
   private final StringBuilder mReceivingLine;


   public DiscourseLogger(int var1) {
      StringBuilder var2 = new StringBuilder(100);
      this.mReceivingLine = var2;
      this.mBufferSize = var1;
      this.initBuffer();
   }

   private void addLine(String var1) {
      String[] var2 = this.mBuffer;
      int var3 = this.mPos;
      var2[var3] = var1;
      int var4 = this.mPos + 1;
      this.mPos = var4;
      int var5 = this.mPos;
      int var6 = this.mBufferSize;
      if(var5 >= var6) {
         this.mPos = 0;
      }
   }

   private void addReceivingLineToBuffer() {
      if(this.mReceivingLine.length() > 0) {
         String var1 = this.mReceivingLine.toString();
         this.addLine(var1);
         StringBuilder var2 = this.mReceivingLine.delete(0, Integer.MAX_VALUE);
      }
   }

   private void initBuffer() {
      String[] var1 = new String[this.mBufferSize];
      this.mBuffer = var1;
   }

   public void addReceivedByte(int var1) {
      if(32 <= var1 && var1 <= 126) {
         StringBuilder var2 = this.mReceivingLine;
         char var3 = (char)var1;
         var2.append(var3);
      } else if(var1 == 10) {
         this.addReceivingLineToBuffer();
      } else if(var1 != 13) {
         StringBuilder var5 = (new StringBuilder()).append("00");
         String var6 = Integer.toHexString(var1);
         String var7 = var5.append(var6).toString();
         StringBuilder var8 = this.mReceivingLine;
         StringBuilder var9 = (new StringBuilder()).append("\\x");
         int var10 = var7.length() - 2;
         int var11 = var7.length();
         String var12 = var7.substring(var10, var11);
         String var13 = var9.append(var12).toString();
         var8.append(var13);
      }
   }

   public void addSentCommand(String var1) {
      this.addLine(var1);
   }

   String[] getLines() {
      this.addReceivingLineToBuffer();
      ArrayList var1 = new ArrayList();
      int var2 = this.mPos;
      int var3 = this.mPos;

      do {
         String var4 = this.mBuffer[var3];
         if(var4 != null) {
            var1.add(var4);
         }

         int var6 = var3 + 1;
         int var7 = this.mBufferSize;
         var3 = var6 % var7;
      } while(var3 != var2);

      String[] var8 = new String[var1.size()];
      var1.toArray(var8);
      return var8;
   }

   public void logLastDiscourse() {
      if(this.getLines().length != 0) {
         int var1 = Log.w("Email", "Last network activities:");
         String[] var2 = this.getLines();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String var5 = var2[var4];
            int var6 = Log.w("Email", var5);
         }

         this.initBuffer();
      }
   }
}
